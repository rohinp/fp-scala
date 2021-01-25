package day4.basics

object EnforcingSequenceAvoidDL extends App {
  import day4.basics.UidSync._
  import day4.basics.Utilities._

  class Account(val name: String, var money: Int) {
    val uid: Long = getUid
  }

  def send(account1: Account, account2: Account, n: Int) = {
    def adjust(): Unit = {
      account1.money -= n
      account2.money += n
      log("--")
    }

    if (account1.uid < account2.uid)
      account1.synchronized {
        account2.synchronized {
          adjust()
        }
      }
    else
      account2.synchronized {
        account1.synchronized {
          adjust()
        }
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
