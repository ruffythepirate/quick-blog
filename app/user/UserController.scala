package user

import javax.inject.Inject
import play.api.mvc._

import scala.concurrent.ExecutionContext

class UserController @Inject()(cc: ControllerComponents, userService: UserService)
                              (implicit ec: ExecutionContext)
extends AbstractController(cc) {

  def showLogin() = Action {
    implicit request: Request[AnyContent] =>

      Ok(views.html.user.login())
  }

  def login(userCredentials: UserCredentials) = Action.async {
      implicit request: Request[AnyContent] =>

        userService.verifyCredentials(userCredentials)
          .map {
            case true => {
              Redirect("/", TEMPORARY_REDIRECT)
                .withCookies(Cookie("logged_in", "true"))
                .bakeCookies()
            }
            case false => {
              Ok(views.html.user.login(Some("username or password invalid")))
            }
          }
  }
}
