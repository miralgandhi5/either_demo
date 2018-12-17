package eitherT.example2

import cats.data.EitherT
import cats.implicits._
import eitherT.example2.UserService.UserServiceError
import eitherT.example2.UserService.UserServiceError.{AccountRegistrationFailed, UserNotFound, UsernameAlreadyExists}
import eitherT.example2.entities.{Account, User}

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class UserService(accountService: AccountService)(implicit ec: ExecutionContext) {

  val users: ListBuffer[User] = ListBuffer.empty[User]

  private def validateUsername(name: String): Future[Either[UserServiceError, Unit]] = Future {
    if (users.exists(_.name == name)) {
      Left(UsernameAlreadyExists)
    } else {
      Right()
    }
  }

  private def createUser(name: String, age: Int) = Future {
    val user = User(name, age)
    users += user
    user.name
  }

  def getUser(username: String): Future[Option[User]] = Future {
    users.find(_.name == username)
  }

  def register(name: String, age: Int, password: String): Future[Either[UserServiceError, (User, Account)]] = {
    val result = for {
      _ <- EitherT(validateUsername(name))
      username <- EitherT.right[UserServiceError](createUser(name, age))
      account <- EitherT(accountService.register(username, password)).leftMap(_ => AccountRegistrationFailed)
      user <- EitherT.fromOptionF[Future,UserServiceError, User](getUser(username), UserNotFound)
    } yield (user, account)

    result.value
  }

}

object UserService {

  sealed trait UserServiceError

  object UserServiceError {

    case object UsernameAlreadyExists extends UserServiceError

    case object UserNotFound extends UserServiceError

    case object AccountRegistrationFailed extends UserServiceError

  }

}