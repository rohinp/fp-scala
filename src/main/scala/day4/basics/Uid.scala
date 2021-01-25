package day4.basics

object Uid extends App {
  import Utilities._

  /**
    * An example of race condition
    * */
  var uidCounter = 0L
  def getUid: Long = {
    val newUid = uidCounter + 1
    uidCounter = newUid
    newUid
  }

  def printUid(n: Int) = {
    val uids = for (_ <- 1 until n) yield getUid
    log(s"Generated uids are $uids")
  }

  val t = thread { printUid(5) }
  printUid(5)
  t.join()
}
