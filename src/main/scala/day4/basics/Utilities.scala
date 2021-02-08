package day4.basics

object Utilities {
  def log(msg: String): Unit =
    println(s"${Thread.currentThread().getThreadGroup.getName} - ${Thread.currentThread().getName} - ${Thread.currentThread().getPriority}: $msg")

  def thread(body: => Unit): Thread = {
    val t = new Thread(() => body)
    t.start()
    t
  }
}
