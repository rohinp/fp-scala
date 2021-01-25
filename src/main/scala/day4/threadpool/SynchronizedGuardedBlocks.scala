package day4.threadpool

import day4.basics.Utilities.{log, thread}

object SynchronizedGuardedBlocks extends App {
  val lock = new AnyRef
  var message: Option[String] = None
  val greeter = thread {
    lock.synchronized {
      log("Inside greeter")
      while (message == None) {
        log("waiting...")
        lock.wait()
      }
      log(message.get)
    }
  }
  Thread.sleep(100)
  lock.synchronized {
    log("Inside lock sync main")
    message = Some("Hello!")
    lock.notify()
  }
  greeter.join()
}
