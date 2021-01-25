package day4.basics

object SynchronizedNesting extends App {

  import scala.collection._
  import Utilities._

  /**
    * In this example we have seen how nested synchronized blocks can be helpful in concurrency
    *
    * */
  private val transfers = mutable.ArrayBuffer[String]()

  def logTransfer(name: String, n: Int) =
    transfers.synchronized {
      transfers += s"transfer to account $name = $n"
    }

  class Account(val name: String, var money: Int)

  def add(account: Account, n: Int) =
    account.synchronized {
      account.money += n
      if (n > 0) logTransfer(account.name, n)
    }

  val jane = new Account("Jane", 100)
  val john = new Account("John", 200)

  val t1 = thread {
    add(jane, 5)
  }
  val t2 = thread {
    add(john, 50)
  }
  val t3 = thread {
    add(jane, 70)
  }

  t1.join()
  t2.join()
  t3.join()

  log(s"<--- transfers --->\n $transfers")
}
