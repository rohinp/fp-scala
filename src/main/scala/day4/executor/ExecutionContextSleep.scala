package day4.executor

import scala.concurrent.ExecutionContext
import day4.basics.Utilities._
object ExecutionContextSleep extends App {

  def execute(body: =>Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })

  import scala.concurrent._
  for (i <- 0 until 32) execute {
    Thread.sleep(2000)
    log(s"Task $i completed.")
  }
  Thread.sleep(10000)
}