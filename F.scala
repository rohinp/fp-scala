
object F {
	import scala.concurrent.ExecutionContext.Implicits.global
        import scala.concurrent.Future

val doSomething: Future[Unit] = Future{
    println("I'm doing a thing")
  }
	
}
