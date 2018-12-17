package eitherT

import cats.data.EitherT
import cats.implicits._
import either.Example1.{divide, parseDouble}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Example1 extends App {

  def parseDoubleAsync(s: String): Future[Either[String, Double]] =
    Future.successful(parseDouble(s))

  def divideAsync(a: Double, b: Double): Future[Either[String, Double]] =
    Future.successful(divide(a, b))

  def divisionProgramAsync(inputA: String, inputB: String): Future[Either[String, Double]] = {
    val result: EitherT[Future, String, Double] = for {
      a <- EitherT(parseDoubleAsync(inputA))
      b <- EitherT(parseDoubleAsync(inputB))
      result <- EitherT(divideAsync(a, b))
    } yield result

    result.value
  }

 divisionProgramAsync("4", "2").map(res => println(s"Result when we divide 4 by 2 is $res"))
 divisionProgramAsync("4", "0").map(res => println(s"Result when we divide 4 by 0 is $res"))
 divisionProgramAsync("4", "a").map(res => println(s"Result when we try to divide with a string is $res"))

  Thread.sleep(1000)
}
