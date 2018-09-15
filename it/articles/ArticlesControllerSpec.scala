package articles

import org.joda.time.DateTime
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.slick.DatabaseConfigProvider
import play.api.http.HttpVerbs
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._
import slick.basic.DatabaseConfig
import util.{DatabaseHelper, TestData}

class ArticlesControllerSpec extends PlaySpec
  with GuiceOneAppPerSuite
  with Injecting
  with BeforeAndAfterAll
  with HttpVerbs
  with TestData
  with DatabaseHelper {

  var articleInDb = Article(None, "Title of article", "Text of article", None, Some(DateTime.now), Some(DateTime.now))

  def dbConfig: DatabaseConfig[Nothing] = databaseConfigProvider.get

  var databaseConfigProvider: DatabaseConfigProvider = _

  override def beforeAll = {
    val injector = fakeApplication().injector
    databaseConfigProvider = injector.instanceOf(classOf[DatabaseConfigProvider])

    val user = addUser(ANY_USER)
    articleInDb = addArticle(articleInDb.copy(userId = user.id))
  }

  "ArticlesController.showArticle(:id) GET" when {
    "Article exists" should {
      "render the article" in {
        val controller = inject[ArticlesController]
        val home = controller.showArticle(articleInDb.id.get).apply(FakeRequest(GET, "/"))

        status(home) mustBe OK
        contentType(home) mustBe Some("text/html")
        contentAsString(home) must include(articleInDb.title)
        contentAsString(home) must include(articleInDb.text)
      }
    }
  }
}
