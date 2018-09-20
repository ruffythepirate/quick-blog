package user

import org.scalatestplus.play.PlaySpec

class PasswordServiceSpec extends PlaySpec {

  val cut = new PasswordService()
  "PasswordServiceSpec" should {

    "generate deterministic hashed password" in {

      val first = cut.generateHashedPassword("hello", "worldylong")
      val second = cut.generateHashedPassword("hello", "worldylong")

      first must equal(second)
    }

    "generate unique hashed passwords for different passwords" in {

      val first = cut.generateHashedPassword("hello", "worldylong")
      val second = cut.generateHashedPassword("hello2", "worldylong")

      first mustNot equal(second)
    }
    "generate unique hashed passwords for different salts" in {

      val first = cut.generateHashedPassword("hello", "worldylong")
      val second = cut.generateHashedPassword("hello", "worldylong2")

      first mustNot equal(second)
    }


    "generateSalt" in {
      val saltOne = cut.generateSalt()
      val saltTwo = cut.generateSalt()

      saltOne mustNot equal(saltTwo)
    }

  }
}
