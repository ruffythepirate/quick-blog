package user

import javax.inject.Inject
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.ExecutionContext

class UserController @Inject()(cc: ControllerComponents)
                              (implicit ec: ExecutionContext)
extends AbstractController(cc) {

  def showLogin() = Action {
    implicit request: Request[AnyContent] =>

      Ok(views.html.user.login())
  }

  def login(credentials: UserCredentials) = Action.async {
      implicit request: Request[AnyContent] =>
      ???
  }
}
