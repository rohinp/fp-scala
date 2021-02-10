package day4.executor

import day4.basics.Utilities.log

import java.util.concurrent.{Executor, ForkJoinPool}

object ExecutorsCreate extends App {
  /**
   * Executor is a convenient way to run a task inside a thread
   * Executor object decides on which thread and when to call the run method.
   *  1. Lets create an example executor and run some tasks
   *
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
   *
   *  1. A executor implementation which creates worker(thread) which are daemon, the thread are executed till jvm exists
   *  1. Executor executes the submitted task in current thread, s separate thread or later from a que of tasks depending on the implementation
   *  1. Executor execute method is eager in evaluation check ExecutorTry object for examples
   *
   *  <b>Important</b> Be careful on making the tasks submitted to executor context to be non blocking otherwise you might end up having a starvation scenario
   **/



}
