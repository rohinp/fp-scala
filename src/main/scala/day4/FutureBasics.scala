package day4

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import day4.basics.Utilities._
object FutureBasics extends App {
  /*
  disclaimer: I assume people are aware of concurrency on JVM.

  Expectations is the participants are already aware about how to create/run a Thread, Thread state and pit falls.

  In either case the day4 package contains a lot of examples and notes from Learning Concurrent programming in Scala book
  which is enough to understand the basics.
  */

  //First Step:
  //Important: Running programs on a separate JVM process then sbt

  /**
   * what and why about thread pool
   *  1. implementations of Executor (executor.ExecutorCreate)
   * the defaults in scala
   * Issues with thread pool (starvation on blocking tasks)
   * */


  /**
   * Brief introduction to Union types in Scala
   * Either and Try
  */

  //1. Concurrency using Future and Promise in scala
    //What is a Future
      /**
       * 1. imagining future as a place holder
       * 2. difference between future computations and future value
       * 3. Example reading sbt file
      * */

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
