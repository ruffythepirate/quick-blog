package util

import articles.{Article, ArticlesQuery}
import org.scalatest.concurrent.ScalaFutures
import slick.basic.DatabaseConfig
import user.{User, UsersQuery}

trait DatabaseHelper extends ScalaFutures with ArticlesQuery with UsersQuery{
   def dbConfig: DatabaseConfig[Nothing]

  import slick.jdbc.PostgresProfile.api._

  def cleanDatabase = {
    System.out.println("Cleaning up database...")
    val deletedArticles = dbConfig.db.run(
      articles.delete
    ).futureValue

    System.out.println(s"Removed ${deletedArticles} articles.")

    val deletedUsers = dbConfig.db.run(
      users.delete
    ).futureValue

    System.out.println(s"Removed ${deletedUsers} users.")
   }

  private val insertUserQuery = users returning users.map(_.id) into ((item, id) => item.copy(id = Some(id)))
  def addUser(user: User): User = {
    dbConfig.db.run(
      insertUserQuery += user
    ).futureValue
  }

  private val insertArticleQuery = articles returning articles.map(_.id) into ((item, id) => item.copy(id = Some(id)))
  def addArticle(article:Article): Article = {
    dbConfig.db.run(
      insertArticleQuery += article
    ).futureValue
  }
}
