package controllers

import javax.inject.Inject

import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import views.html.surveys.Survey

import scala.concurrent.duration._

class Surveys @Inject() (config: Configuration, ws: WSClient) extends Controller {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get

  private case class PublicSurveyParameters(localeId: String)

  private implicit val surveyParamReads = Json.reads[PublicSurveyParameters]

  def survey(surveyId: String, genUser: Option[String]) = Action.async {

    ws.url(s"$apiBaseUrl/user/surveys/$surveyId/public-parameters").withRequestTimeout(10 seconds).get.map {
      response =>
        response.status match {
          case 200 => {
            Json.fromJson[PublicSurveyParameters](Json.parse(response.body)) match {
              case JsSuccess(params, _) => Ok(Survey(surveyId, params.localeId, apiBaseUrl))
              case JsError(_) => InternalServerError("Internal Server Error")
            }
          }
          case 404 => NotFound("Page Not Found")
          case _ => InternalServerError("Internal Server Error")
        }
    }
  }
}
