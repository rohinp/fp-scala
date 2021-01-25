package day4.threadpool

import day4.basics.Utilities.log

object SynchronizedBadPool extends App {

  import scala.collection._

  private val tasks = mutable.Queue[() => Unit]()
  val worker = new Thread {
    def poll(): Option[() => Unit] =
      tasks.synchronized {
        if (tasks.nonEmpty) Some(tasks.dequeue()) else None
      }

    override def run() =
      while (true) poll() match {
        case Some(task) => task()
        case None       =>
      }
  }
  worker.setName("Worker--")
  worker.setDaemon(true)
  worker.start()

  def asynchronous(body: => Unit) =
    tasks.synchronized {
      tasks.enqueue(() => body)
    }

  asynchronous {
    log("Hello")
  }
  asynchronous {
    log(" world1")
  }
  Thread.sleep(100)
}
