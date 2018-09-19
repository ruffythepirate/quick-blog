package user

import com.google.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository: UserRepository)(implicit ec: ExecutionContext) {

  def verifyCredentials(userCredentials: UserCredentials): Future[Boolean] = {
    userRepository.getUserWithCredentials(userCredentials.email)
      .map(userOpt => {
        userOpt.exists(user => {
          user.password == userCredentials.password
        })
      })
  }
}
