package controllers

import javax.inject.Inject

import com.google.gwt.core.server.StackTraceDeobfuscator
import play.api.{Configuration, Logger}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, BodyParsers, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import upickle.default._

import scala.concurrent.Future

case class ClientStackTraceElement(className: String, fileName: String, lineNumber: Int, methodName: String) {
  def toStackTraceElement = new StackTraceElement(className, methodName, fileName, lineNumber)
}

case class ClientException(className: String, message: String, stackTrace: List[ClientStackTraceElement])

case class ErrorReport(surveyId: String, userName: Option[String], gwtPermutationStrongName: String, exceptionChain: List[ClientException])

case class ForwardErrorReport(surveyId: String, userName: Option[String], exceptionChain: List[ClientException])

class Errors @Inject()(config: Configuration, ws: WSClient) extends Controller with UpickleUtil {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get

  val deobfuscator = StackTraceDeobfuscator.fromResource("META-INF/gwt-deploy/survey/symbolMaps")

  def report() = Action.async(upickleBodyParser[ErrorReport]) {
    request =>
      Future {

        val report = request.body

        val deobfuscatedChain = report.exceptionChain.map {
          exception =>
            val deobfuscatedStackTrace = exception.stackTrace.map {
              ste =>
                val d = deobfuscator.resymbolize(ste.toStackTraceElement, report.gwtPermutationStrongName)
                ClientStackTraceElement(d.getClassName, d.getFileName, d.getLineNumber, d.getMethodName)
            }
            exception.copy(stackTrace = deobfuscatedStackTrace)
        }

        Ok(write(ForwardErrorReport(report.surveyId, report.userName, deobfuscatedChain)))
      }
  }
}
