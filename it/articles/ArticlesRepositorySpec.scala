package articles

import org.joda.time.DateTime
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.basic.DatabaseConfig
import user.User
import util.{DatabaseHelper, TestData}

class ArticlesRepositorySpec extends PlaySpec
  with GuiceOneAppPerSuite
  with Injecting
  with BeforeAndAfterAll
  with ScalaFutures
  with TestData
  with DatabaseHelper {


  var cut: ArticlesRepository = _

  val ANY_DATE = DateTime.now()
  var userInDb:User = _

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(3, Seconds), interval = Span(5, Millis))

  def dbConfig: DatabaseConfig[Nothing] = databaseConfigProvider.get

  var databaseConfigProvider : DatabaseConfigProvider = _



  override def beforeAll() = {

    val injector = fakeApplication().injector
    cut = injector.instanceOf(classOf[ArticlesRepository])

    databaseConfigProvider = injector.instanceOf(classOf[DatabaseConfigProvider])
    cleanDatabase

    userInDb = addUser(ANY_USER)
  }

  override def afterAll() = {
    cleanDatabase
  }

  "ArticlesRepository" should {

    "insert a readable row in" in {
      val insertedArticle = cut.insertArticle(ANY_ARTICLE.copy(userId = userInDb.id)).futureValue

      val readArticle = cut.selectArticle(insertedArticle.id.get).futureValue

      insertedArticle.toViewModel(userInDb) mustEqual readArticle
    }

    "return all articles" in {
      cleanDatabase
      userInDb = addUser(ANY_USER)

      val allArticles = Seq(ANY_ARTICLE.copy(title = "title 1", userId = userInDb.id),
                            ANY_ARTICLE.copy(title = "title 2", userId = userInDb.id),
                            ANY_ARTICLE.copy(title = "title 3", userId = userInDb.id))

      val insertedArticles = allArticles.map(item =>
        cut.insertArticle(item).futureValue
      ).map(a => a.toViewModel(userInDb))

      val readArticles = cut.selectAll().futureValue

      readArticles must contain theSameElementsAs(insertedArticles)
    }
  }
}
