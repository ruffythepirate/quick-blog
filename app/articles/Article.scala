package articles

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import user.UserWithCredentials
import util.JodaDateTimeJsonConverter

case class Article(id: Option[Int], title: String, text: String, userId: Option[Int], updated: Option[DateTime], created: Option[DateTime]) {
  def toViewModel(user: UserWithCredentials): ArticleViewModel = {
    ArticleViewModel(id.get, title, text, user.name, created.get)
  }
}

object ArticleFormat extends JodaDateTimeJsonConverter {
  implicit val format: Format[Article] = Json.format[Article]
}
