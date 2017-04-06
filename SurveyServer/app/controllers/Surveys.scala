package controllers

import javax.inject.Inject

import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import views.html.errors.{Error404, Error500}
import views.html.surveys.Survey

import scala.concurrent.duration._

case class PublicSurveyParameters(localeId: String, supportEmail: String)

class Surveys @Inject()(config: Configuration, ws: WSClient) extends Controller {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get

  private implicit val surveyParamReads = Json.reads[PublicSurveyParameters]

  def survey(surveyId: String, genUser: Option[String]) = Action.async {

    ws.url(s"$apiBaseUrl/surveys/$surveyId/public-parameters").withRequestTimeout(30 seconds).get.map {
      response =>
        response.status match {
          case 200 => {
            Json.fromJson[PublicSurveyParameters](Json.parse(response.body)) match {
              case JsSuccess(params, _) => Ok(Survey(surveyId, params, apiBaseUrl))
              case JsError(_) => InternalServerError
            }
          }
          case 404 => NotFound(Error404())
          case _ => InternalServerError(Error500())
        }
    }
  }
}
