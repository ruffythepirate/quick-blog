package user

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

class UserRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext)
  extends UsersQuery
{
  import slick.jdbc.PostgresProfile.api._

  def getUser(email: String): Future[User] = {
    val dbConfig = dbConfigProvider.get

    dbConfig.db.run(
      users.filter(u => u.email === email).result.head
    )
  }

}
