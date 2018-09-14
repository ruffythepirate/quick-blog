package articles

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.slick.DatabaseConfigProvider
import play.api.test.Injecting
import slick.basic.DatabaseConfig
import util.DatabaseHelper

class ArticlesRepositorySpec extends PlaySpec
  with GuiceOneAppPerSuite
  with Injecting
  with BeforeAndAfterAll
  with ScalaFutures
  with DatabaseHelper {


  var cut: ArticlesRepository = _

  val ANY_ARTICLE = Article(None, "title", "text", None, None)

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(3, Seconds), interval = Span(5, Millis))

  def dbConfig: DatabaseConfig[Nothing] = databaseConfigProvider.get

  var databaseConfigProvider : DatabaseConfigProvider = _



  override def beforeAll() = {
    val injector = fakeApplication().injector
    cut = injector.instanceOf(classOf[ArticlesRepository])

    databaseConfigProvider = injector.instanceOf(classOf[DatabaseConfigProvider])
  }

  override def afterAll() = {
    cleanDatabase
  }

  "ArticlesRepository" should {

    "insert a readable row in" in {
      val insertedArticle = cut.insertArticle(ANY_ARTICLE).futureValue

//      val readArticle = cut.selectArticle(insertedArticle.id.get).futureValue

//      insertedArticle.copy(created = None, updated = None) mustEqual readArticle.copy(created = None, updated = None)
    }

    "return all articles" in {
      cleanDatabase

      val allArticles = Seq(ANY_ARTICLE.copy(title = "title 1"),
      ANY_ARTICLE.copy(title = "title 2"),
      ANY_ARTICLE.copy(title = "title 3"))

      val insertedArticles = allArticles.map(item =>
        cut.insertArticle(item).futureValue
      )

      val readArticles = cut.selectAll().futureValue

//      compareWithoutDate(insertedArticles, readArticles)
    }
  }

  def compareWithoutDate(expected: Seq[Article], actual: Seq[Article]): Unit = {
    val neutralExpected = expected.map(a => a.copy(created = None, updated = None))
    val neutralActual = actual.map(a => a.copy(created = None, updated = None))

    neutralActual must contain theSameElementsAs(neutralExpected)
  }
}
