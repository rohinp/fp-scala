package day4.basics

object SynchronizedDeadLock extends App {

  import SynchronizedNesting.Account
  import Utilities._

  /**
    * Example of a dead lock where both threads are waiting for each other
    *
    * check EnforcingSequenceAvoidDL.scala for avoiding dead lock for below situations
    * */
  def send(account1: Account, account2: Account, n: Int) =
    account1.synchronized {
      account2.synchronized {
        account1.money -= n
        account2.money += n
      }
    }

  val a = new Account("Jack", 1000)
  val b = new Account("Jill", 2000)

  val t1 = thread {
    for (i <- 1 until 100) send(a, b, 1)
  }
  val t2 = thread {
    for (i <- 1 until 100) send(b, a, 1)
  }

  t1.join()
  t2.join()

  log(s" a = ${a.money} and b = ${b.money}")
}
