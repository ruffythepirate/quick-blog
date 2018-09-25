package user

import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository: UserRepository)(implicit ec: ExecutionContext) {

  def verifyCredentials(userCredentials: UserCredentials): Future[Option[Int]] = {
    userRepository.getUserWithCredentials(userCredentials.email)
      .map(userOpt => {
        userOpt.flatMap(user => {
          if (user.password == userCredentials.password) {
            Some(user)
          }
          else {
            None
          }
        }).flatMap(user => user.id)
      })
  }
}
