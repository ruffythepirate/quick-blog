package articles

import org.joda.time.DateTime
import users.User

case class Article(id: Option[Int], title: String, text: String, userId: Option[Int], updated: Option[DateTime], created: Option[DateTime]) {
  def toViewModel(user: User): ArticleViewModel = {
    ArticleViewModel(id.get, title, text, user.name, created.get)
  }
}
