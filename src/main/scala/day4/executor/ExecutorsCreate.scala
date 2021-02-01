package day4.executor

import day4.basics.Utilities._

import java.util.concurrent._

object ExecutorsCreate extends App {
  /**
   * Executor is a convenient way to run a task inside a thread
   *  1. Lets create an example executor and run some tasks
   *
   * There are different factory methods to create a thread pool (Executors class)
   *
   *  1. newFixedThreadPool
   *  1. newWorkStealingPool
   *  1. newCachedThreadPool
   *  1. newSingleThreadExecutor
   *
   *  1. Fork/Join framework java.util.concurrent.ForkJoinPool
   *
   *  <b>Note</b> : If none of the executors provided by the above factory methods meet your needs,
   *  constructing instances of java.util.concurrent.ThreadPoolExecutor or java.util.concurrent.ScheduledThreadPoolExecutor will give you additional options.
   **/
  val executor: Executor = new java.util.concurrent.ForkJoinPool

  executor.execute(new Runnable {
    def run() = log("This task is run asynchronously.1")
  })
  executor.execute(new Runnable {
    def run() = log("This task is run asynchronously.2")
  })

  Thread.sleep(500)

}
