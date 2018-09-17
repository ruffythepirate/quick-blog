package util

import articles.Article
import user.User

trait TestData {

  val ANY_USER = User(None, "name", "email")

  val ANY_ARTICLE = Article(None, "title", "text", None, None, None)
}
