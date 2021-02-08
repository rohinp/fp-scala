package day4.executor
import day4.basics.Utilities._
object AtomicUid extends App {
  import java.util.concurrent.atomic._
  private val uid = new AtomicLong(0L)

  def getUniqueId(): Long = uid.incrementAndGet()

  execute {
    log(s"Got a unique id asynchronously: ${getUniqueId()}")
  }

  log(s"Got a unique id: ${getUniqueId()}")
}
