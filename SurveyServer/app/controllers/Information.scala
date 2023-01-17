package controllers

import javax.inject.Inject
import _root_.info.PageNames
import guice.CustomContent
import play.api.Configuration
import play.api.mvc.{Action, Controller, RequestHeader}
import play.twirl.api.Html
import views.html.info._


class Information @Inject()(customContent: CustomContent, config: Configuration) extends Controller {

  private val gaTrackingCode = config.getString("intake24.ga.trackingCode")
  private val supportEmail = config.getString("intake24.supportEmail").get
  private val videoURL = config.getString("intake24.videoURL").get
  private val infoPageLocaleDisclaimer = config.getString("intake24.infoPageLocaleDisclaimer")


  private def renderInfoPage(title: String, content: Html)(implicit request: RequestHeader): Html = {
    InfoPageLayout(title, content, customContent.footer(), videoURL, gaTrackingCode, infoPageLocaleDisclaimer)
  }

  def landing = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.landing, LandingContent(supportEmail)))
  }

  def recall = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.recall, RecallContent()))
  }

  def features = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.features, FeaturesContent()))
  }

  def openSource = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.openSource, OpenSourceContent(supportEmail)))
  }

  def output = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.output, OutputContent()))
  }

  def validation = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.validation, ValidationContent()))
  }

  def localisation = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.localisation, LocalisationContent()))
  }

  def feedback = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.feedback, FeedbackContent()))
  }

  def publications = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.publications, PublicationsContent()))
  }

  def contacts = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.contacts, ContactsContent(supportEmail)))
  }

  def privacy = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.privacy, customContent.privacy()))
  }

  def terms = Action {
    implicit request =>
      Ok(renderInfoPage(PageNames.terms, customContent.termsAndConditions()))
  }

}
