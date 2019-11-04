package app

import javax.inject.Inject

import play.api.Configuration
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent._
import javax.inject.Singleton

import views.html.errors.{Error404, Error500}


@Singleton
class ErrorHandler @Inject()(config: Configuration) extends HttpErrorHandler {

  private val supportEmail = config.getString("intake24.supportEmail").get

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)(Error404(supportEmail))
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      InternalServerError(Error500(supportEmail))
    )
  }
}