package articles

import org.joda.time.DateTime
import org.scalatest.{FunSuite, MustMatchers}
import org.scalatestplus.play.PlaySpec
import user.UserWithCredentials
import util.TestData

class ArticleSpec extends PlaySpec with MustMatchers with TestData{

  "Article.toViewModel" should {
    "translate correctly" in {
      val updated = DateTime.now().plusDays(4)
      val created = DateTime.now()
      val article = Article(Some(3), "title", "text", Some(1), Some(updated), Some(created))

      val result = article.toViewModel(ANY_USER.copy(id = Some(1)))

      result.id mustEqual  3
      result.title mustEqual  "title"
      result.text mustEqual  "text"
      result.author mustEqual  ANY_USER.name
      result.created mustEqual  created
    }
  }
}