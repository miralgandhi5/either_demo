package either

import scala.util.{Failure, Success}

object Example1 extends App {

  import scala.util.Try

  def parseDouble(s: String): Either[String, Double] =
    Try(s.toDouble).map( value => Right(value)).getOrElse(Left(s"$s is not a number"))

  def divide(a: Double, b: Double): Either[String, Double] =
    Either.cond(b != 0, a / b, "Cannot divide by zero")

  def divisionProgram(inputA: String, inputB: String): Either[String, Double] =
    for {
      a <- parseDouble(inputA)
      b <- parseDouble(inputB)
      result <- divide(a, b)
    } yield result

  println("Result when we divide 4 by 2 is "+ divisionProgram("4", "2"))
  println("Result when we divide 4 by 0 is "+ divisionProgram("4", "0"))
  println("Result when we try to divide with a string is " + divisionProgram("4", "a"))

}
