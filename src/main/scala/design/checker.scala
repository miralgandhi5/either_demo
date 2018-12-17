package design

import cats.data.EitherT

import scala.concurrent.{ExecutionContext, Future}

trait checker {
  implicit val ec: ExecutionContext
  def show(string: String): EitherT[Future, Unit, Unit]
}
