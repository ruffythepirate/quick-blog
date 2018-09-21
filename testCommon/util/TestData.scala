package util

import articles.Article
import user.UserWithCredentials

trait TestData {

  val ANY_USER = UserWithCredentials(None, "name", "email", "password", "", None)

  val ANY_ARTICLE = Article(None, "title", "text", None, None, None)
}
