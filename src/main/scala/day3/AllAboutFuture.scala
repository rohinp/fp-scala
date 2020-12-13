package day3

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AllAboutFuture {

  /**
  * <p>Notes:
  * <p>Awaitable is the parent class of Future, before we jump on Future lets  give a glance on Awaitable
  * <p> As the name implies it provides a mechanism to block the current thread util the Awaitable is completed
   * Awaitable provides two methods namely ready and result:
   * <p><b>Important</b>
   * <p><b>these methods should not directly be used, instead call the methods on the Await object</b>
   *
   *
  * */
  val doSomething: Future[Unit] = Future{
    println("I'm doing a thing")
  }
}
