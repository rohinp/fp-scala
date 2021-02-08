package day4.executor

import java.util.concurrent.{ExecutorService, ForkJoinPool, TimeUnit}
import day4.basics.Utilities._
object ExecutorServiceTry extends App {
  val executor:ExecutorService = new ForkJoinPool

  for {
    x <- 1 to 32
  } yield {
    executor.execute(() => {
      Thread.sleep(1000)
      log(s"Task $x no delay 100")
    })
  }

  executor.shutdown()

  //executor.awaitTermination(50, TimeUnit.MILLISECONDS)

  Thread.sleep(2000)
  log("Main thread done...")
}
