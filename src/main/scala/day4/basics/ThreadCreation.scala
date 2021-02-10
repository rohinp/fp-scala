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

}
