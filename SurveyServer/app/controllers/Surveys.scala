package controllers

import javax.inject.Inject

import play.api.{Configuration, Logger}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import views.html.errors.{Error404, Error500}
import views.html.surveys.{Survey, SurveyFeedback}

import scala.concurrent.duration._

case class PublicSurveyParameters(localeId: String, respondentLanguageId: String, supportEmail: String, originatingURL: Array[String])

class Surveys @Inject()(config: Configuration, ws: WSClient) extends Controller {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get
  private val gaTrackingCode = config.getString("intake24.ga.trackingCode")

  private implicit val surveyParamReads = Json.reads[PublicSurveyParameters]

  def survey(surveyId: String, genUser: Option[String]) = Action.async {

    ws.url(s"$apiBaseUrl/surveys/$surveyId/public-parameters").withRequestTimeout(30 seconds).get.map {
      response =>
        response.status match {
          case 200 => {
            Json.fromJson[PublicSurveyParameters](Json.parse(response.body)) match {
              case JsSuccess(params, _) => Ok(Survey(surveyId, params, apiBaseUrl, gaTrackingCode))
              case JsError(p) =>
                Logger.error("Could not parse API server public survey parameters response")
                Logger.error("Response body: " + response.body)
                Logger.error("Error: " + p.toString)
                InternalServerError
            }
          }
          case x =>
            Logger.error("API server responded with something other than 200")
            Logger.error("Response code: " + x)
            new Status(x)
        }
    }
  }

  def surveyFeedbackPage(surveyId: String) = Action {
    Ok(SurveyFeedback(apiBaseUrl, surveyId, gaTrackingCode))
  }

}
