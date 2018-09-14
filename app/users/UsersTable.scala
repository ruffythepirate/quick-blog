package users

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

case class User(id: Option[Int], name: String, email: String)

class UsersTable (tag: Tag)
  extends Table[User](tag, "users"){

  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def email: Rep[String] = column[String]("email")

  def * : ProvenShape[User] = (id.?, name, email) <> (User.tupled, User.unapply)
}

trait UsersQuery {
  import slick.jdbc.PostgresProfile.api._

  val users: TableQuery[UsersTable] = TableQuery[UsersTable]
}
