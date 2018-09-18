package user

import java.sql.Timestamp

import org.joda.time.DateTime
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

case class User(id: Option[Int], name: String, email: String, password: String, lastLogin: Option[DateTime])

class UsersTable (tag: Tag)
  extends Table[User](tag, "users"){

  implicit val JodaDateTimeMapper = MappedColumnType.base[DateTime, Timestamp](
    dt => new Timestamp(dt.getMillis),
    ts => new DateTime(ts.getTime())
  )

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def email: Rep[String] = column[String]("email")

  def password: Rep[String] = column[String]("password")

  def lastLogin: Rep[DateTime] = column[DateTime]("last_login")

  def * : ProvenShape[User] = (id.?, name, email, password, lastLogin.?) <> (User.tupled, User.unapply)
}

trait UsersQuery {
  import slick.jdbc.PostgresProfile.api._

  val users: TableQuery[UsersTable] = TableQuery[UsersTable]
}
