package controllers

import javax.inject.Inject

import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}

class GeneratedConfig @Inject()(config: Configuration, ws: WSClient) extends Controller {

  private val apiBaseUrl = config.getString("intake24.apiBaseUrl").get

  def generatedConfig() = Action {
    Ok(views.js.generatedConfig.config(apiBaseUrl))
  }
}
