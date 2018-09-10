package articles

import java.sql.Timestamp

import org.joda.time.DateTime
import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api._


case class Article(id: Option[Int], title: String, text: String, updated: Option[DateTime], created: Option[DateTime])

class ArticlesTable(tag: Tag)
  extends Table[Article](tag, "articles") {

  implicit val JodaDateTimeMapper = MappedColumnType.base[DateTime, Timestamp](
    dt => new Timestamp(dt.getMillis),
    ts => new DateTime(ts.getTime())
  )

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def title: Rep[String] = column[String]("title")

  def text: Rep[String] = column[String]("text")

  def updated: Rep[DateTime] = column[DateTime]("updated", O.AutoInc)

  def created: Rep[DateTime] = column[DateTime]("created", O.AutoInc)

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[Article] = (id.?, title, text, updated.?, created.?) <> (Article.tupled, Article.unapply)
}

trait ArticlesQuery {

  import slick.jdbc.PostgresProfile.api._

  val articles: TableQuery[ArticlesTable] = TableQuery[ArticlesTable]
}
