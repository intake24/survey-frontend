package controllers.landing

import play.api.mvc.{Action, Controller}
import views.html.landing.Home

class LandingPageController extends Controller {

  def home = Action {
    request =>
      Ok(Home())
  }
}
