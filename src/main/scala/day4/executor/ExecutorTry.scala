package day4.executor

import java.util.concurrent.{Executor, ForkJoinPool}
import day4.basics.Utilities._

object ExecutorTry extends App {
  //running the task in current thread
  val executor1 = new Executor {
    override def execute(command: Runnable): Unit = command.run()
  }

  //running thread in separate thread
  val executor2 = new Executor {
    override def execute(command: Runnable): Unit = thread(command.run())
  }

  val executor = new ForkJoinPool

  executor1.execute(() => {
    Thread.sleep(100)
    log("Current Thread Executor")
  })
  executor2.execute(() => {
    Thread.sleep(100)
    log("New Thread Executor")
  })

  for {
    x <- 1 to 10
  } yield{
    executor.execute(() => {
      Thread.sleep(10)
      log(s"Fork Join Executor $x")
    })
  }

}
