package user

import com.google.inject.Inject

class UserService @Inject() (userRepository: UserRepository) {

  def verifyCredentials(userCredentials: UserCredentials): Boolean = {
   ???
  }
}
