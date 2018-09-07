package articles

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

class ArticlesRepository @Inject() ( protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext)
  extends ArticlesQuery {

  import slick.jdbc.PostgresProfile.api._
  val insertQuery = articles returning articles.map(_.id) into ((item, id) => item.copy(id = Some(id)))

  def insertArticle(article: Article) :Future[Article] = {
    val dbConfig = dbConfigProvider.get

    dbConfig.db.run(
      insertQuery += article
    )
  }

  def selectArticle(id: Int): Future[Article] = {
     val dbConfig = dbConfigProvider.get

    dbConfig.db.run(
      articles.filter(_.id === id).result.head
    )
  }

}
