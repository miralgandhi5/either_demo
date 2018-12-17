package design

import java.time.Instant

import cats.data.EitherT
import cats.implicits._

import scala.concurrent.Future


trait firstChild extends checker {

  abstract override def show(string: String): EitherT[Future, Unit, Unit] = {
    println("I was called2"+string + Instant.now())
    val ex = super.show(string) orElse {
      string match {
        case "left" => EitherT.leftT[Future, Unit](())
        case "right" => EitherT.rightT[Future, Unit](())
      }
    }
    println("I was called 2" + string + Instant.now())
    ex
  }

}