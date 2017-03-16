package controllers

import javax.inject.Inject

import akka.util.CompactByteString
import com.google.gwt.core.server.StackTraceDeobfuscator
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.{InMemoryBody, WSClient}
import play.api.mvc.{Action, Controller}
import upickle.default._

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.duration._


class Errors @Inject()(config: Configuration, ws: WSClient) extends Controller with UpickleUtil {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get

  case class ClientStackTraceElement(className: String, fileName: String, lineNumber: Int, methodName: String) {
    def toStackTraceElement = new StackTraceElement(className, methodName, fileName, lineNumber)
  }

  case class ClientException(className: String, message: String, stackTrace: List[ClientStackTraceElement])

  case class ErrorReport(surveyId: String, userName: Option[String], gwtPermutationStrongName: String, exceptionChain: Seq[ClientException], surveyStateJSON: String)

  private case class ForwardErrorReport(userId: Option[String], surveyId: Option[String], stackTrace: Seq[String], surveyStateJSON: String)

  private val deobfuscator = StackTraceDeobfuscator.fromResource("META-INF/gwt-deploy/survey/symbolMaps")

  private def buildStackTrace(gwtPermutation: String, exceptionChain: Seq[ClientException]): Future[Seq[String]] =
    Future {
      val stackTrace = mutable.Buffer[String]()

      exceptionChain.foreach {
        exception =>

          val exceptionDesc = s"${exception.className}: ${exception.message}"

          if (!stackTrace.isEmpty)
            stackTrace.append(s"Caused by $exceptionDesc")
          else
            stackTrace.append(s"Exception $exceptionDesc")

          exception.stackTrace.foreach {
            ste =>
              val d = deobfuscator.resymbolize(ste.toStackTraceElement, gwtPermutation)

              stackTrace.append(s"  at ${d.getClassName}.${d.getMethodName}(${d.getFileName}:${d.getLineNumber})")
          }
      }

      stackTrace
    }

  def report() = Action.async(upickleBodyParser[ErrorReport]) {
    request =>
      for (stackTrace <- buildStackTrace(request.body.gwtPermutationStrongName, request.body.exceptionChain);
           apiResponse <- ws.url(s"$apiBaseUrl/errors/report")
             .withRequestTimeout(10 seconds)
             .withHeaders(("Content-Type", "application/json"))
             .withBody(InMemoryBody(CompactByteString(write(ForwardErrorReport(request.body.userName, Some(request.body.surveyId), stackTrace, request.body.surveyStateJSON)))))
             .execute("POST")
      ) yield Status(apiResponse.status)
  }
}
