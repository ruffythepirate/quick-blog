package user

import org.scalatest.BeforeAndAfterAll
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

class UserServiceSpec extends PlaySpec with MockitoSugar{


  val userRepository = mock[UserRepository]

  val cut = new UserService(userRepository)

  "UserService.verifyCredentials" should {
    "return false" when {
      "password doesn't match" in {
      }

      "no user is found" in {

      }
    }

    "return true" when {
      "password match" in {

      }
    }
  }

}
