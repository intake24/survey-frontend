package controllers

import java.time.Instant
import java.time.format.DateTimeFormatter

import play.api.Logger
import play.api.http.ContentTypes
import play.api.mvc.{BodyParser, BodyParsers, Result, Results}
import upickle.default._
import upickle.{Invalid, Js}

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

trait UpickleUtil {

  import BodyParsers.parse._

  protected implicit val dateWriter = Writer[Instant] {
    case t => Js.Str(DateTimeFormatter.ISO_INSTANT.format(t))
  }


  def parseJson[T](json: String)(implicit reader: Reader[T]): Either[Result, T] = {
    try {
      Right(read[T](json))
    } catch {
      case Invalid.Data(_, msg) => {
        Logger.debug(s"Invalid data: $msg")
        Left(Results.BadRequest)
      }
      case Invalid.Json(msg, _) => {
        Logger.debug(s"Invalid JSON: $msg")
        Left(Results.BadRequest)
      }
      case e: Throwable => {
        Logger.debug(s"JSON parser exception", e)
        Left(Results.BadRequest)
      }
    }
  }

  def withParsedValue[T](json: String)(block: T => Result)(implicit reader: Reader[T]): Result =
    parseJson[T](json) match {
      case Right(value) => block(value)
      case Left(errorResult) => errorResult
    }

  def upickleBodyParser[T](implicit reader: Reader[T]): BodyParser[T] =
    when(
      request => request.contentType.exists(_.equalsIgnoreCase(ContentTypes.JSON)),
      tolerantText,
      _ => Future.successful(Results.UnsupportedMediaType)).validate {
      stringBody =>
        parseJson[T](stringBody)
    }
}