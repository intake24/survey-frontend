/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package security

import be.objectify.deadbolt.scala.DeadboltHandler
import play.api.mvc.Request
import play.api.mvc.Results
import scala.concurrent.Future
import models.User
import be.objectify.deadbolt.scala.DynamicResourceHandler
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.Inject


import play.api.mvc.Result

import upickle.default._
import play.api.libs.json._
import play.Logger
import models.SecurityInfo
import be.objectify.deadbolt.scala.models.Subject

import com.mohiva.play.silhouette.api.Environment
import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import be.objectify.deadbolt.scala.AuthenticatedRequest


class DeadboltHandlerImpl(val env: Environment[Intake24ApiEnv]) extends DeadboltHandler {

  def beforeAuthCheck[A](request: Request[A]) = Future(None)

  override def getDynamicResourceHandler[A](request: Request[A]): Future[Option[DynamicResourceHandler]] = Future(None)

  override def getSubject[A](request: AuthenticatedRequest[A]): Future[Option[Subject]] = {
    env.authenticatorService.retrieve(request).map {
      _.flatMap {
        auth =>
          if (auth.isValid) auth.customClaims.map {
            customClaims =>
              val roles = (customClaims \ "i24r").get.as[Set[String]]
              val permissions = (customClaims \ "i24p").get.as[Set[String]]
              new User(auth.loginInfo.providerKey, SecurityInfo(roles, permissions))
          }
          else None
      }
    }
  }

  def onAuthFailure[A](request: AuthenticatedRequest[A]): Future[Result] =
    env.authenticatorService.retrieve(request).map {
      case Some(auth) if auth.isValid => Results.Forbidden
      case _ => Results.Unauthorized.withHeaders(("WWW-Authenticate", "X-Auth-Token"))
    }
}
