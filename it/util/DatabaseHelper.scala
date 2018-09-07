package util

import articles.{ArticlesQuery, ArticlesTable}
import org.scalatest.concurrent.ScalaFutures
import slick.basic.DatabaseConfig

import scala.concurrent.ExecutionContext

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
}
