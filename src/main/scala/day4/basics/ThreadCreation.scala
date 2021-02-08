package day4.basics

object ThreadCreation extends App {
  import Utilities._
  /**
    * Basic thread creation
    * on start method call the thread goes in Runnable state
    *
    * thread have different states, when you do a join on the thread it puts the host thread in wait state
    * On the other hand sleep method puts the current thread in the timed wait state
    *
    * Understanding different states of the thread is important and we will list all those as we see more examples
    * > Important
    * Threads can be created using Thread class by extending or by Runnable interface
    * It is never legal to start a thread more than once.
    * In particular, a thread may not be restarted once it has completed execution.
    *
    * */
  class MyThread extends Thread {
    override def run(): Unit = log("User Thread - no delay")
  }
  val threadGroup = new ThreadGroup("NewGroup")
  val t = new MyThread

  val t1 = new Thread(threadGroup ,() => {
    Thread.sleep(200)
    log("Demon with  join")
  })
  val t3 = new Thread(() => {
    log("Demon with no join no delay")
  })
  val t2 = new Thread(threadGroup, () => {
    Thread.sleep(1000)
    log("User Thread")
  })
  t1.setDaemon(true)
  t3.setDaemon(true)
  t.start()
  t1.start()
  t2.start()
  t1.join()
  log("This is main")
}
