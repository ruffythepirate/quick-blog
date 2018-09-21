package user

import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import play.api.http.HttpVerbs
import play.api.mvc.Results
import play.api.test.{FakeRequest, StubControllerComponentsFactory}
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.mockito.Mockito.when
import play.api.test.Helpers._

import scala.concurrent.Future;

class UserControllerSpec extends PlaySpec with MockitoSugar  with StubControllerComponentsFactory with HttpVerbs with Results with ScalaFutures{


  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global

  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout =  Span(3, Seconds), interval = Span(5, Millis))
  implicit val timeout = Timeout(Span(3, Seconds))


  val userService = mock[UserService]

  val ANY_CREDENTIALS = UserCredentials("user", "password")

  val cut: UserController = new UserController(stubControllerComponents(), userService)

  "UserController" when {
    "wrong credentials" should {
      "not set cookie" in {
        when(userService.verifyCredentials(ANY_CREDENTIALS)).thenReturn(Future.successful(false))

        val loginResult = cut.login(ANY_CREDENTIALS).apply(FakeRequest(POST, "/"))

        cookies(loginResult).size mustBe 0
      }

      "return a page saying user or password not found" in {
        when(userService.verifyCredentials(ANY_CREDENTIALS)).thenReturn(Future.successful(false))

        val loginResult = cut.login(ANY_CREDENTIALS).apply(FakeRequest(POST, "/"))

        contentAsString(loginResult) must include ("username or password invalid")
      }
    }

    "correct credentials" should {
      "set credentials" in {
        when(userService.verifyCredentials(ANY_CREDENTIALS)).thenReturn(Future.successful(true))

        val loginResult = cut.login(ANY_CREDENTIALS).apply(FakeRequest(POST, "/"))

        cookies(loginResult).size mustBe 1
      }

      "redirect to home page" in {
        when(userService.verifyCredentials(ANY_CREDENTIALS)).thenReturn(Future.successful(true))

        val loginResult = cut.login(ANY_CREDENTIALS).apply(FakeRequest(POST, "/"))

        status(loginResult) must equal(TEMPORARY_REDIRECT)
      }
    }
  }

}
