package day4.threadpool

import day4.basics.Utilities

object SynchronizedPool extends App {

  import scala.collection._
  import Utilities._

  private val tasks = mutable.Queue[() => Unit]()

  object Worker extends Thread {
    setDaemon(true)

    def poll() =
      tasks.synchronized {
        while (tasks.isEmpty) tasks.wait()
        tasks.dequeue()
      }

    override def run() =
      while (true) {
        val task = poll()
        task()
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

  scala.io.StdIn.readLine()
}
