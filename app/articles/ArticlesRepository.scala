package articles

import com.google.inject.Inject
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import user.{UserWithCredentials, UsersQuery}

import scala.concurrent.{ExecutionContext, Future}

class ArticlesRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit ec: ExecutionContext)
  extends ArticlesQuery with UsersQuery {

  import slick.jdbc.PostgresProfile.api._

  val insertQuery = articles returning articles.map(_.id) into ((item, id) => item.copy(id = Some(id)))
  val selectQuery =       for {
        (a, u) <- articles join users on (_.userId === _.id)
      } yield (a, u)

  def selectMapping(article: Article, user: UserWithCredentials) = {
    article.toViewModel(user)
  }

  def insertArticle(article: Article): Future[Article] = {
    val dbConfig: DatabaseConfig[Nothing] = dbConfigProvider.get

    dbConfig.db.run(
      insertQuery += article.copy(created = Some(DateTime.now), updated = Some(DateTime.now))
    )
  }

  def updateArticle(id: Int, article: Article): Future[Article] = {
    ???
  }

  def selectArticle(id: Int): Future[ArticleViewModel] = {
    val dbConfig = dbConfigProvider.get


    dbConfig.db.run(
      selectQuery.filter{case (article,_) => article.id === id}.result.head
    ).map { case (article: Article, user: UserWithCredentials) => selectMapping(article, user) }
  }

  def selectAll(): Future[Seq[ArticleViewModel]] = {
    val dbConfig = dbConfigProvider.get


   dbConfig.db.run(
      selectQuery.result
    ).map(result => result.map{case(article:Article, user:UserWithCredentials)=>selectMapping(article, user)})
  }

}
