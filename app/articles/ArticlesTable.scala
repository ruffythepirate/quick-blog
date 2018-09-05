package articles

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

case class Article(id: Option[Int], title: String, text: String)

class ArticlesTable(tag: Tag)
  extends Table[Article](tag, "articles") {

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title: Rep[String] = column[String]("title")
  def text: Rep[String] = column[String]("text")
//  def updated: Rep[DateTime] = column[DateTime]("updated")
//  def created: Rep[DateTime] = column[DateTime]("created")

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[Article] = (id.?, title, text) <> (Article.tupled, Article.unapply)
}

trait ArticlesQuery {
  val articles = TableQuery[ArticlesTable]
}
