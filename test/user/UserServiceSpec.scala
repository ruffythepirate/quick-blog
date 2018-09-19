package user

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import util.TestData
import org.mockito.Mockito._

import scala.concurrent.{ExecutionContext, Future}

class UserServiceSpec extends PlaySpec with ScalaFutures with MockitoSugar with TestData {

  implicit val ec = ExecutionContext.Implicits.global
  val userRepository: UserRepository = mock[UserRepository]


  val cut = new UserService(userRepository)

  "UserService.verifyCredentials" should {
    "return false" when {
      "no user exists" in {
        when(userRepository.getUserWithCredentials(ANY_USER.email))
          .thenReturn(Future.successful(Option.empty[UserWithCredentials]))

        val result = cut.verifyCredentials(UserCredentials(ANY_USER.email, ANY_USER.password)).futureValue

        result must equal(false)
      }

      "password is wrong" in {
        when(userRepository.getUserWithCredentials(ANY_USER.email))
          .thenReturn(Future.successful(Some(ANY_USER)))

        val result = cut.verifyCredentials(UserCredentials(ANY_USER.email, "other" + ANY_USER.password)).futureValue

        result must equal(false)
      }
    }

    "return true" when {
      "password is correct" in {
        when(userRepository.getUserWithCredentials(ANY_USER.email))
          .thenReturn(Future.successful(Some(ANY_USER)))

        val result = cut.verifyCredentials(UserCredentials(ANY_USER.email, ANY_USER.password)).futureValue

        result must equal(true)
      }
    }
  }

}
