package user

import org.joda.time.DateTime

case class UserWithCredentials(id: Option[Int], name: String, email: String, password: String, salt: String, lastLogin: Option[DateTime])

