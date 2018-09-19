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
  var userInDb: UserWithCredentials = _

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

  "UserRepository.getUserWithCredentials" should {
    "get a user based on email" in {
      userInDb = addUser(ANY_USER.copy(email = "cool email"))
      val readUser = cut.getUserWithCredentials(userInDb.email).futureValue

      readUser must equal(Some(userInDb))
    }
  }
}

