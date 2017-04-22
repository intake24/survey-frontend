package controllers

import javax.inject.Inject

import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}

class SurveyFeedback @Inject()(config: Configuration, ws: WSClient) extends Controller {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get

  def feedback() = Action {
    Ok(views.html.surveyFeedback.index(apiBaseUrl))
  }
}
