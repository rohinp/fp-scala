package day4

import Utilities._

object ThreadCreation extends App {
  class MyThread extends Thread {
    override def run(): Unit = log("New Thread running")
  }
  val t = new MyThread
  t.start()
  t.join()
  log("New thread joined")
}
