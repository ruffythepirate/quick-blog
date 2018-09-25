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

  val ANY_USER_WITH_ID = ANY_USER.copy(id = Some(1))

  val cut = new UserService(userRepository)

  "UserService.verifyCredentials" should {
    "return false" when {
      "no user exists" in {
        when(userRepository.getUserWithCredentials(ANY_USER_WITH_ID.email))
          .thenReturn(Future.successful(Option.empty[UserWithCredentials]))

        val result = cut.verifyCredentials(UserCredentials(ANY_USER_WITH_ID.email, ANY_USER_WITH_ID.password)).futureValue

        result must equal(None)
      }

      "password is wrong" in {
        when(userRepository.getUserWithCredentials(ANY_USER_WITH_ID.email))
          .thenReturn(Future.successful(Some(ANY_USER_WITH_ID)))

        val result = cut.verifyCredentials(UserCredentials(ANY_USER_WITH_ID.email, "other" + ANY_USER_WITH_ID.password)).futureValue

        result must equal(None)
      }
    }

    "return true" when {
      "password is correct" in {
        when(userRepository.getUserWithCredentials(ANY_USER_WITH_ID.email))
          .thenReturn(Future.successful(Some(ANY_USER_WITH_ID)))

        val result = cut.verifyCredentials(UserCredentials(ANY_USER_WITH_ID.email, ANY_USER_WITH_ID.password)).futureValue

        result must equal(Some(ANY_USER_WITH_ID.id.get))
      }
    }
  }

}
