package user

import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.basic.DatabaseConfig
import util.{DatabaseHelper, TestData}

class UserRepositorySpec extends PlaySpec
  with GuiceOneAppPerSuite
  with Injecting
  with BeforeAndAfterAll
  with BeforeAndAfter
  with ScalaFutures
  with TestData
  with DatabaseHelper {

  var cut: UserRepository = _
  var userInDb: User = _

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(3, Seconds), interval = Span(5, Millis))

  def dbConfig: DatabaseConfig[Nothing] = databaseConfigProvider.get

  var databaseConfigProvider: DatabaseConfigProvider = _


  override def beforeAll() = {

    val injector = fakeApplication().injector
    cut = injector.instanceOf(classOf[UserRepository])

    databaseConfigProvider = injector.instanceOf(classOf[DatabaseConfigProvider])

  }

  before {
    cleanDatabase
  }

  override def afterAll() = {
    cleanDatabase
  }

  "UserRepository.getUser" should {
    "get a user based on email" in {
      userInDb = addUser(ANY_USER.copy(email = "cool email"))
      val readUser = cut.getUser(userInDb.email).futureValue

     readUser must equal (userInDb)
    }
  }

  "UserRepository.verifyCredentials" should {
    "return false" when {
      "no user found" in {
        val result = cut.verifyCredentials(UserCredentials("unknown", "")).futureValue

        result must equal(false)
      }

      "credentials don't match" in {
        addUser(ANY_USER.copy(email = "hello", password = "secret"))

        val result = cut.verifyCredentials(UserCredentials("hello", "wrong secret")).futureValue

        result must equal(false)
      }
    }

    "return true" when {
      "credentials match" in {
        addUser(ANY_USER.copy(email = "hello", password = "secret"))

        val result = cut.verifyCredentials(UserCredentials("hello", "secret")).futureValue

        result must equal(true)
      }
    }
  }
}
