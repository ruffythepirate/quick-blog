package articles

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatest.mockito.MockitoSugar
import play.api.http.HttpVerbs
import play.api.test.{FakeRequest, StubControllerComponentsFactory}
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.Future

class ArticlesControllerSpec extends PlaySpec with BeforeAndAfter with MockitoSugar with StubControllerComponentsFactory with HttpVerbs with ScalaFutures{

  implicit def executionContext = scala.concurrent.ExecutionContext.Implicits.global

  implicit val defaultPatience =
    PatienceConfig(timeout =  Span(3, Seconds), interval = Span(5, Millis))

  var cut: ArticlesController = _

  var articlesRepository: ArticlesRepository = _

  val ANY_ARTICLE = Article(Some(1), "title", "text")

  before {
    articlesRepository = mock[ArticlesRepository]
    cut = new ArticlesController( stubControllerComponents(), articlesRepository)
  }

  "ArticlesController" when {
    "calling showArticle" should {
      "read article from db" in {
        when(articlesRepository.selectArticle(1)).thenReturn(Future.successful(ANY_ARTICLE))

        val articlePage = cut.showArticle(1).apply(FakeRequest(GET, "/")).futureValue

        verify(articlesRepository).selectArticle(1)
      }

//      "use the markdown service" in {
//      }
    }
  }

}
