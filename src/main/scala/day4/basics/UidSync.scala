package day4.basics

object UidSync extends App {
  import Utilities._

  /**
    * Avoiding race condition with synchronized block.
    * Remember that synchronized block solves the problem but it also add extra CPU cycles to achieve that
    *
    * Every object has a monitor/intrinsic lock, when a thread wants to run a synchronized block it needs to acquire a lock on the monitor
    * these locks are mutually exclusive
    *
    * Synchronized block when accessed by a thread can cause other thread who is desiring to take a lock on same object goes into blocked state
    * */
  var uidCounter = 0L

  def getUid: Long =
    this.synchronized {
      val newUid = uidCounter + 1
      uidCounter = newUid
      newUid
    }

  def printUid(n: Int) = {
    val uids = for (_ <- 1 until n) yield getUid
    log(s"Generated uids are $uids")
  }

  val t = thread {
    printUid(5)
  }
  printUid(5)
  t.join()
}
