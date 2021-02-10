package day4.promises

object PromisesAndCustomOperations extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import day4.basics.Utilities._
import scala.concurrent.duration._
  implicit class FutureOps[T](val self: Future[T]) {
    def or(that: Future[T]): Future[T] = {
      val p = Promise[T]
      self onComplete { case x => p tryComplete x }
      that onComplete { case y => p tryComplete y }
      p.future
    }
  }

  val f = Future { "now" } or Future { "later" }

  f foreach {
    case when => log(s"The future is $when")
  }

}