package day4.threadpool

import day4.basics.Utilities

object SynchronizedGracefulShutdown extends App {

  import scala.collection._
  import scala.annotation.tailrec
  import Utilities._

  private val tasks = mutable.Queue[() => Unit]()

  object Worker extends Thread {
    var terminated = false

    def poll(): Option[() => Unit] =
      tasks.synchronized {
        while (tasks.isEmpty && !terminated) tasks.wait()
        if (!terminated) Some(tasks.dequeue()) else None
      }

    @tailrec override def run() =
      poll() match {
        case Some(task) => task(); run()
        case None       =>
      }

    def shutdown() =
      tasks.synchronized {
        terminated = true
        tasks.notify()
      }
  }

  Worker.start()

  def asynchronous(body: => Unit) =
    tasks.synchronized {
      tasks.enqueue(() => body)
      tasks.notify()
    }

  asynchronous {
    log("Hello ")
  }
  asynchronous {
    log("World!")
  }

  Thread.sleep(1000)

  Worker.shutdown()
}
