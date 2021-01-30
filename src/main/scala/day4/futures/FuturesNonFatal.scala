package day4.futures

object FuturesNonFatal extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import day4.basics.Utilities._

  val f = Future { throw new InterruptedException("") }
  val f1 = Future { throw new OutOfMemoryError("") }
  val g = Future { throw new IllegalArgumentException }

  f.failed foreach { case t => log(s"error - $t") }
  g.failed foreach { case t => log(s"error - $t") }

}