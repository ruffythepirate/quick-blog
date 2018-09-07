package util

import articles.{Article, ArticlesQuery}
import org.scalatest.concurrent.ScalaFutures
import slick.basic.DatabaseConfig

trait DatabaseHelper extends ScalaFutures with ArticlesQuery{
   def dbConfig: DatabaseConfig[Nothing]

  import slick.jdbc.PostgresProfile.api._

  def cleanDatabase = {
    System.out.println("Cleaning up database...")
    val deletedArticles = dbConfig.db.run(
      articles.delete
    ).futureValue

    System.out.println(s"Removed ${deletedArticles} articles.")
  }

  val insertQuery = articles returning articles.map(_.id) into ((item, id) => item.copy(id = Some(id)))
  def addArticle(article:Article): Article = {
    dbConfig.db.run(
      insertQuery += article
    ).futureValue
  }
}
