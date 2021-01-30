package day4.executor

import day4.basics.Utilities._

import java.util.concurrent._

object ExecutorsCreate extends App {
  /**
   * Executor is a convenient way to run a task inside a thread
   *  1. Lets create an example executor and run some tasks
   * There are different implementations of executor
   *  1. ForkJoinPool
   * */
  val executor: Executor = new java.util.concurrent.ForkJoinPool

  executor.execute(new Runnable {
    def run() = log("This task is run asynchronously.1")
  })
  executor.execute(new Runnable {
    def run() = log("This task is run asynchronously.2")
  })

  Thread.sleep(500)

}
