package guice

import com.google.inject.Singleton
import play.twirl.api.Html
import views.html.info.newcastle.{Footer, PrivacyContent, TermsContent}

@Singleton
class NewcastleCustomContent extends CustomContent {
  override def footer(): Html = Footer()
  override def privacy(): Html = PrivacyContent()
  override def termsAndConditions(): Html = TermsContent()
}
