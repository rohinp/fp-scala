package day4.basics
import java.io.PrintWriter
import java.io.StringWriter

object Utilities {
  def log(msg: String): Unit =
    println(s"${Thread.currentThread().getThreadGroup.getName} - ${Thread.currentThread().getName} - ${Thread.currentThread().getPriority}: $msg")

  def thread(body: => Unit): Thread = {
    val t = new Thread(() => body)
    t.start()
    t
  }

  def stackTraceToString(e:Throwable): String = {
    val sw = new StringWriter
    e.printStackTrace(new PrintWriter(sw))
    sw.toString
  }
}
