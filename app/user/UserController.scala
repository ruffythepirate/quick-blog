package user

import javax.inject.Inject
import play.api.data.Form
import play.api.mvc._

import scala.concurrent.ExecutionContext

import play.api.data._
import play.api.data.Forms._

class UserController @Inject()(cc: ControllerComponents,
                               userService: UserService,
                               cookieService: CookieService)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  val credentialsForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(UserCredentials.apply)(UserCredentials.unapply)
  )

  def showLogin() = Action {
    implicit request: Request[AnyContent] =>

      Ok(views.html.user.login())
  }

  def login() =
    Action.async {
      implicit request: Request[AnyContent] =>

        val userCredentials = credentialsForm.bindFromRequest().get

        userService.verifyCredentials(userCredentials)
          .map {
            case Some(id) => {
              Redirect("/", TEMPORARY_REDIRECT)
                .withCookies(
                  cookieService.createGoldenCookie("userId", id.toString)
                )
                .bakeCookies()
            }
            case None => {
              Ok(views.html.user.login(Some("username or password invalid")))
            }
          }
    }
}
