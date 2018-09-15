package articles.snippets

import articles.ArticleViewModel
import org.joda.time.DateTime
import org.scalatest.{Matchers, MustMatchers}
import org.scalatest.words.ShouldVerb
import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers._

class ArticleItemSpec extends PlaySpec {

  val ANY_DATE = DateTime.now
  val ANY_ARTICLE = ArticleViewModel(1, "title", "text", "author", ANY_DATE)

  def renderArticle = {
    val renderedContent = _root_.views.html.articles.snippets.articleItem(ANY_ARTICLE)
    renderedContent.toString()
  }
  "articleItem" should {
    "render title" in {
      renderArticle must include(ANY_ARTICLE.title)
    }

    "render time" in {
      renderArticle must include(ANY_ARTICLE.created.toString("YYYY-MM-dd"))
    }

    "render author name" in {
      renderArticle must include(ANY_ARTICLE.author)
    }
  }



}
