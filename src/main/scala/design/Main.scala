package design

import scala.concurrent.ExecutionContext.Implicits.global


object Main extends App {
  val obj = new album() with secondChild with firstChild {
  }
  obj.show("right")

  Thread.sleep(1000)
}
