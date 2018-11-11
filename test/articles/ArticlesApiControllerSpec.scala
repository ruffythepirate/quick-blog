package articles

import akka.util.Timeout
import org.joda.time.DateTime
import org.mockito.Mockito
import org.scalatest.BeforeAndAfter
import org.scalatest.concurrent.PatienceConfiguration.{Timeout => PatienceTimeout}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.PlaySpec
import play.api.http.HttpVerbs
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContent, Request, Results}
import play.api.test.{FakeRequest, Helpers, StubControllerComponentsFactory}
import util.{IdEntity, IdEntityFormat}

import scala.concurrent.Future

class ArticlesApiControllerSpec extends PlaySpec with BeforeAndAfter with MockitoSugar with StubControllerComponentsFactory with HttpVerbs with ScalaFutures with Results {

  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global

  implicit val defaultPatience: PatienceConfig =
    PatienceConfig(timeout = Span(3, Seconds), interval = Span(5, Millis))
  implicit val timeout: PatienceTimeout = PatienceTimeout(Span(3, Seconds))

  var cut: ArticlesApiController = _

  var articlesRepository: ArticlesRepository = _

  var ANY_ARTICLE = Article(None, "Title of article", "Text of article", None, Some(DateTime.now), Some(DateTime.now))

  val ANY_ARTICLE_ID = 1

  before {
    articlesRepository = mock[ArticlesRepository]
    cut = new ArticlesApiController(stubControllerComponents(), articlesRepository)
  }

  implicit val articleFormat = ArticleFormat.format
  implicit val idEntityFormat = IdEntityFormat.format

  "ArticlesApiController" when {
    "calling POST /api/articles" should {

      "create an article" in {
        Mockito.when(articlesRepository.insertArticle(ANY_ARTICLE))
          .thenReturn(Future.successful(ANY_ARTICLE.copy(id = Some(ANY_ARTICLE_ID))))
        val request: Request[AnyContent] = FakeRequest("POST", "/api/articles")
          .withJsonBody(Json.toJson(ANY_ARTICLE))

        cut.createArticle().apply(request).futureValue

        Mockito.verify(articlesRepository).insertArticle(ANY_ARTICLE)
      }

      "returns id for article" in {
        Mockito.when(articlesRepository.insertArticle(ANY_ARTICLE))
          .thenReturn(Future.successful(ANY_ARTICLE.copy(id = Some(ANY_ARTICLE_ID))))
        val request: Request[AnyContent] = FakeRequest("POST", "/api/articles")
          .withJsonBody(Json.toJson(ANY_ARTICLE))

        val result = cut.createArticle().apply(request)

        Helpers.contentAsJson(result)(Timeout(Span(3, Seconds))) mustEqual Json.toJson(IdEntity(ANY_ARTICLE_ID))
      }
    }

    "calling PUT /api/articles/:id" should {

      "update an article" in {
        Mockito.when(articlesRepository.updateArticle(ANY_ARTICLE_ID, ANY_ARTICLE))
          .thenReturn(Future.successful(ANY_ARTICLE))
         val request: Request[AnyContent] = FakeRequest("PUT", "/api/articles/:id")
          .withJsonBody(Json.toJson(ANY_ARTICLE))

        cut.updateArticle(ANY_ARTICLE_ID).apply(request).futureValue

        Mockito.verify(articlesRepository).updateArticle(ANY_ARTICLE_ID, ANY_ARTICLE)
      }
    }
  }
}
