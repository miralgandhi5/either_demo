package design

import java.time.Instant

import cats.data.EitherT
import cats.implicits._

import scala.concurrent.Future

trait secondChild extends checker {

  abstract override def show(string: String): EitherT[Future, Unit, Unit] = {
    println("I was called 3" + string + Instant.now())
    val ex = super.show(string) orElse {
      string match{
        case "left" => EitherT.rightT[Future, Unit](())
        case "right" => EitherT.leftT[Future, Unit](())
      }
    }
    println("I was called 3" + string + Instant.now())
    ex
  }

}
