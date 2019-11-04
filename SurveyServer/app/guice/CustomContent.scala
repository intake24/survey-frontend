package guice

import play.twirl.api.Html

trait CustomContent {
  def footer(): Html
  def privacy(): Html
  def termsAndConditions(): Html
}
