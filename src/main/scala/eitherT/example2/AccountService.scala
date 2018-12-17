package eitherT.example2

import cats.data.EitherT
import cats.implicits._
import eitherT.example2.AccountService.AccountServiceError
import eitherT.example2.AccountService.AccountServiceError.{AccountNotFound, InvalidPassword, UsernameAlreadyExists}
import eitherT.example2.entities.Account

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class AccountService(implicit ec: ExecutionContext) {

  val accounts: ListBuffer[Account] = ListBuffer.empty[Account]

  private def validatePassword(password: String): Either[AccountServiceError, Unit] =
    if (password.length < 5) {
      Left(InvalidPassword)
    } else {
      Right()
    }

  private def createAccount(name: String, password: String) = Future {
    val account = Account(name, password)
    accounts += account
    account.username
  }

  def getAccount(username: String): Future[Option[Account]] = Future {
    accounts.find(_.username == username)
  }

  def register(name: String, password: String): Future[Either[AccountServiceError, Account]] = {
    val result = for {
      _ <- EitherT.fromEither[Future](validatePassword(password))
      username <- EitherT.right[AccountServiceError](createAccount(name, password))
      account <- EitherT.fromOptionF[Future,AccountServiceError, Account](getAccount(username), AccountNotFound)
    } yield account

    result.value
  }


}

object AccountService {

  sealed trait AccountServiceError

  object AccountServiceError {

    case object UsernameAlreadyExists extends AccountServiceError

    case object InvalidPassword extends AccountServiceError

    case object AccountNotFound extends AccountServiceError

  }

}
