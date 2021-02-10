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
   * */

  /**
   * Brief introduction to disjunctions/union types in Scala
   * Either and Try
  */

  //1. Concurrency using Future and Promise in scala
    //What is a Future (Asynchronous computation)
      /**
       * 1. imagining future as a place holder
       * 2. difference between future computations and future value
       * 3. First example of future
       * 4. What is a Promise?
       * 5. Lets create our own future
       * 6. future call back bridge pattern
       * 7.
      * */

  /*
  Notes:
  * 1. Future has a companion object and a trait
  *   a. Future.apply is the factory method in the companion object
  *   b. onSuccess, onFailure
  *   c. combinators of future
  *   d. for expression
  *   e. sequencing future
  *   f. Future is eager in nature (not lazy)
  *   g. Future is not a monad (not really imp at this point)
  * 2. Awaitable is the parent of Future trait
  *   i. So that you can await or ready on a result
  *
  * Note: Await must only be used for testing purpose, and real application future call backs and combinators are go to solutions
  *   <b> Await blocks the calling(or example Main) thread
  *
  * */

  //https://stackoverflow.com/questions/32987855/what-are-advantages-of-a-twitter-future-over-a-scala-future
}
