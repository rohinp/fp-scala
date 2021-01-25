package day4.basics
import Utilities._
object ThreadReorder extends App {
  /*
   * The crazy situation which generally must not happen
   * But a big but, because of how the optimizations are implemented inorder to run a thread by a CPU
   * there are chances that there is an interleaving between two threads of execution
   * And chances are both the thread make true for each other and the assert comes to be true
   * Rare but it's quite possible and cause the program to be in a state which we can not reason about
   * */

  for (i <- 1 until 100000) {
    var a = false
    var b = false
    var x = -1
    var y = -1
    val t1 = thread {
      a = true
      y = if (b) 0 else 1
    }
    val t2 = thread {
      b = true
      x = if (a) 0 else 1
    }
    t1.join()
    t2.join()
    println(s"x  = $x , y = $y")
    assert(!(x == 1 && y == 1), s"x  = $x , y = $y")
  }
}
