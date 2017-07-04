package controllers

import javax.inject.Inject

import _root_.info.PageNames
import play.api.Configuration
import play.api.mvc.{Action, Controller}
import views.html.info._


class Information @Inject()(config: Configuration) extends Controller {

  private val gaTrackingCode = config.getString("intake24.ga.trackingCode")

  def landing = Action {
    Ok(InfoPageLayout(PageNames.landing, LandingContent(), gaTrackingCode))
  }

  def recall = Action {
    Ok(InfoPageLayout(PageNames.recall, RecallContent(), gaTrackingCode))
  }

  def features = Action {
    Ok(InfoPageLayout(PageNames.features, FeaturesContent(), gaTrackingCode))
  }

  def openSource = Action {
    Ok(InfoPageLayout(PageNames.openSource, OpenSourceContent(), gaTrackingCode))
  }

  def output = Action {
    Ok(InfoPageLayout(PageNames.output, OutputContent(), gaTrackingCode))
  }

  def validation = Action {
    Ok(InfoPageLayout(PageNames.validation, ValidationContent(), gaTrackingCode))
  }

  def localisation = Action {
    Ok(InfoPageLayout(PageNames.localisation, LocalisationContent(), gaTrackingCode))
  }

  def feedback = Action {
    Ok(InfoPageLayout(PageNames.feedback, FeedbackContent(), gaTrackingCode))
  }

  def publications = Action {
    Ok(InfoPageLayout(PageNames.publications, PublicationsContent(), gaTrackingCode))
  }

  def contacts = Action {
    Ok(InfoPageLayout(PageNames.contacts, ContactsContent(), gaTrackingCode))
  }

  def privacy = Action {
    Ok(InfoPageLayout(PageNames.privacy, PrivacyContent(), gaTrackingCode))
  }

  def terms = Action {
    Ok(InfoPageLayout(PageNames.terms, TermsContent(), gaTrackingCode))
  }

}
