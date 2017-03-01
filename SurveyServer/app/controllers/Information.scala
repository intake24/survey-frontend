package controllers

import _root_.info.PageNames
import play.api.mvc.{Action, Controller}
import views.html.info._

class Information extends Controller {

  def landing = Action {
    Ok(InfoPageLayout(PageNames.landing, LandingContent()))
  }

  def recall = Action {
    Ok(InfoPageLayout(PageNames.recall, RecallContent()))
  }

  def features = Action {
    Ok(InfoPageLayout(PageNames.features, FeaturesContent()))
  }

  def openSource = Action {
    Ok(InfoPageLayout(PageNames.openSource, OpenSourceContent()))
  }

  def output = Action {
    Ok(InfoPageLayout(PageNames.output, OutputContent()))
  }

  def validation = Action {
    Ok(InfoPageLayout(PageNames.validation, ValidationContent()))
  }

  def localisation = Action {
    Ok(InfoPageLayout(PageNames.localisation, LocalisationContent()))
  }

  def feedback = Action {
    Ok(InfoPageLayout(PageNames.feedback, FeedbackContent()))
  }

  def publications = Action {
    Ok(InfoPageLayout(PageNames.publications, PublicationsContent()))
  }

  def contacts = Action {
    Ok(InfoPageLayout(PageNames.contacts, ContactsContent()))
  }
}
