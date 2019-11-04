package guice

import com.google.inject.Singleton
import javax.inject.Inject
import play.api.Configuration
import play.twirl.api.Html
import views.html.info.mrc.{Footer, PrivacyContent, TermsContent}


@Singleton
class MRCCustomContent @Inject()(config: Configuration) extends CustomContent {

  private val supportEmail = config.getString("intake24.supportEmail").get

  override def footer(): Html = Footer()
  override def privacy(): Html = PrivacyContent()
  override def termsAndConditions(): Html = TermsContent(supportEmail)
}
