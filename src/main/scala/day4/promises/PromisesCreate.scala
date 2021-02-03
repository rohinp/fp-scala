package day4.promises

object PromisesCreate extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import day4.basics.Utilities._

  val p = Promise[String]
  val q = Promise[String]

  p.future foreach {
    case text => log(s"Promise p succeeded with '$text'")
  }

  p success "kept"
  
  val secondAttempt = p trySuccess "kept again"

  log(s"Second attempt to complete the same promise went well? $secondAttempt")

  q failure new Exception("not kept")

  q.future.failed foreach {
    case t => log(s"Promise q failed with $t")
  }

}