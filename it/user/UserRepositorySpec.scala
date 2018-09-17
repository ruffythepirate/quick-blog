package user

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.basic.DatabaseConfig
import play.api.test.Helpers._
import util.{DatabaseHelper, TestData}

class UserRepositorySpec extends PlaySpec
  with GuiceOneAppPerSuite
  with Injecting
  with BeforeAndAfterAll
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
    cleanDatabase

  }

  override def afterAll() = {
    cleanDatabase
  }

  "UserRepository" should {
    "get a user based on email" in {
      userInDb = addUser(ANY_USER.copy(email = "cool email"))
      val readUser = cut.getUser(userInDb.email).futureValue

     readUser must equal (userInDb)
    }
  }
}
