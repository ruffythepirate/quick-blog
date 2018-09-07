package articles

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._

case class Article(id: Option[Int], title: String, text: String)

class ArticlesTable(tag: Tag)
  extends Table[Article](tag, "articles") {

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title: Rep[String] = column[String]("title")
  def text: Rep[String] = column[String]("text")

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[Article] = (id.?, title, text) <> (Article.tupled, Article.unapply)
}

trait ArticlesQuery {
  import slick.jdbc.PostgresProfile.api._
  val articles: TableQuery[ArticlesTable] = TableQuery[ArticlesTable]
}
