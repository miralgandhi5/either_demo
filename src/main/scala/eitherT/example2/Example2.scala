package eitherT.example2

import scala.concurrent.ExecutionContext.Implicits.global

object Example2 extends App {

  val accountService = new AccountService()

  val userService = new UserService(accountService)

  userService.register("xyz", 23, "125").map(println)
  // accountService.register("abc", "123").map(println)
  Thread.sleep(1000)
}
