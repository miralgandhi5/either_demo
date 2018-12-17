package design

import java.time.Instant

import cats.data.EitherT
import cats.implicits._

import scala.concurrent.{ExecutionContext, Future}

trait asset {
  implicit val ec: ExecutionContext
}

object asset {

  trait checkerImp extends asset with checker { self =>
    def show(string: String): EitherT[Future, Unit, Unit] = {
      println("I was called 1" + string + Instant.now())
      val ex = string match{
        case "left" =>  EitherT.leftT[Future, Unit](())
        case "right" => EitherT.rightT[Future, Unit](())
      }
      println("I was called 1" + string + Instant.now())
      ex
    }
  }
}
