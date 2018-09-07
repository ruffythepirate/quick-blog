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

  val ANY_ARTICLE = Article(None, "title", "text")

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

      val readArticle = cut.selectArticle(insertedArticle.id.get).futureValue

      insertedArticle mustEqual readArticle
    }
  }

}
