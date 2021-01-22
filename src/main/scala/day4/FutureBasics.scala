package day4

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import Utilities._
object FutureBasics extends App {
  //disclaimer: I assume people are aware of concurrency on JVM

  //little bit out fork true

  //1. Concurrency using Future and Promise in scala
    //What is a Future
  /*
  * 1. Future has a companion object and a trait
  *   a. Future.apply is the factory method in the companion object
  *   b. onSuccess, onFailure
  *   c. combinators of future
  *   d. for expression
  *   e. sequencing future
  *   f. Future is not lazy
  *   g. Future is not a monad
  *   h. Execution context and types
  * 2. Awaitable is the parent of Future trait
  *   i. So that you can await or ready on a result
  *
  * Note: Await must only be used for testing purpose, and real application future call backs and combinators are go to solutions
  *   <b> Await blocks the calling(or example Main) thread
  *
  * */

  Future{
    log("Hello from future")
  }

  log("Hello from main thread")

  scala.io.StdIn.readLine()

}
