package articles

import markdown.MarkdownService
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatest.mockito.MockitoSugar
import play.api.http.HttpVerbs
import play.api.test.{FakeRequest, StubControllerComponentsFactory}
import org.mockito.Mockito._
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class ArticlesControllerSpec extends PlaySpec with BeforeAndAfter with MockitoSugar with StubControllerComponentsFactory with HttpVerbs with ScalaFutures with Results {

  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(3, Seconds), interval = Span(5, Millis))
  implicit val timeout = Timeout(Span(3, Seconds))

  var cut: ArticlesController = _

  var articlesRepository: ArticlesRepository = _
  var markdownService: MarkdownService = _

  val ANY_ARTICLE = Article(Some(1), "title", "text")


  before {
    articlesRepository = mock[ArticlesRepository]
    markdownService = mock[MarkdownService]
    cut = new ArticlesController( stubControllerComponents(), articlesRepository, markdownService)
  }

  "ArticlesController" when {
    "calling showArticle" should {
      "read article from db" in {
        when(articlesRepository.selectArticle(1)).thenReturn(Future.successful(ANY_ARTICLE))

        val articlePage = cut.showArticle(1).apply(FakeRequest(GET, "/")).futureValue

        verify(articlesRepository).selectArticle(1)
      }

      "use the markdown service" in {
        when(articlesRepository.selectArticle(1)).thenReturn(Future.successful(ANY_ARTICLE))
        when(markdownService.renderText(ANY_ARTICLE.text)).thenReturn("markdown reply")

        val articlePage = cut.showArticle(1).apply(FakeRequest(GET, "/"))

        contentAsString(articlePage) must include ("markdown reply")
       }
    }
  }

}
