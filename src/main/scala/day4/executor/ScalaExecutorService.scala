package day4.executor

import day4.basics.Utilities._
object ScalaExecutorService extends App {
  case class MyError(msg:String) extends Exception(msg)
  /**
   * If we want to use failure reporter, prefer using from Executor service method with default executor service object
   * This will allow us to do log/manage exceptions reported the worker threads for the tasks submitted
  * */
  val es = scala.concurrent.ExecutionContext.fromExecutor(null, {
    case MyError(e) => log(s"Error msg we got from thread is: $e")
    case _ => log(s"Un handled exception")
  })

  es.execute(() => log("This is first task"))
  es.execute(() => {
    log("This is error task task")
    throw MyError("Some error in the thread...")
  })
  es.execute(() => log("This is third task"))
  es.execute(() => log("This is forth task"))

  //es.shutdown()
  Thread.sleep(100)
}
