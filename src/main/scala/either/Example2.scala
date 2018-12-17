package either

import either.Example1._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Example2 extends App {

  def parseDoubleAsync(s: String): Future[Either[String, Double]] =
    Future.successful(parseDouble(s))

  def divideAsync(a: Double, b: Double): Future[Either[String, Double]] =
    Future.successful(divide(a, b))

  def divisionProgramAsync(inputA: String, inputB: String): Future[Either[String, Double]] =
    parseDoubleAsync(inputA) flatMap { eitherA =>
      parseDoubleAsync(inputB) flatMap { eitherB =>
        (eitherA, eitherB) match {
          case (Right(a), Right(b)) => divideAsync(a, b)
          case (Left(err), _) => Future.successful(Left(err))
          case (_, Left(err)) => Future.successful(Left(err))
        }
      }
    }

  println("Result when we divide 4 by 2 is "+ divisionProgramAsync("4", "2"))
  println("Result when we divide 4 by 0 is "+ divisionProgramAsync("4", "0"))
  println("Result when we try to divide with a string is " + divisionProgramAsync("4", "a"))

}
