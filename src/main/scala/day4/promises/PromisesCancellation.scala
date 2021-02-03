package day4.promises

object PromisesCancellation extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import day4.basics.Utilities._

  type Cancellable[T] = (Promise[Unit], Future[T])

  def cancellable[T](b: Future[Unit] => T): Cancellable[T] = {
    val cancel = Promise[Unit]
    val f = Future {
        b(cancel.future)
    }
    (cancel, f)
  }

  val (cancel, value) = cancellable { cancel =>
    var i = 0
    while (i < 5) {
      if (cancel.isCompleted) throw new CancellationException
      Thread.sleep(500)
      log(s"$i: working")
      i += 1
    }
    "resulting value"
  }

  Thread.sleep(1500)

  cancel.trySuccess(())

  log("computation cancelled!")
}