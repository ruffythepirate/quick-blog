package user

import java.security.SecureRandom
import java.util.Base64

class PasswordService {

  val random = new SecureRandom()
  def generateSalt(): String = {
    val saltAsBytes = random.generateSeed(20)
    Base64.getEncoder.encodeToString(saltAsBytes)
  }

  def generateHashedPassword(password: String, salt: String): String = {

    val iterations = 1000
    import javax.crypto.SecretKeyFactory
    import javax.crypto.spec.PBEKeySpec
    val chars = password.toCharArray
    val saltAsByteArray = Base64.getDecoder.decode(salt)

    val spec = new PBEKeySpec(chars, saltAsByteArray, iterations, 64 * 8)
    val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hashAsByteArray = skf.generateSecret(spec).getEncoded

    Base64.getEncoder.encodeToString(hashAsByteArray)
  }
}
