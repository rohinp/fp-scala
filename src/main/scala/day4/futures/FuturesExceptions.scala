package day4.futures

object FuturesExceptions extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source
  import day4.basics.Utilities._

  val file = Future { Source.fromFile(".gitignore-SAMPLE").getLines.mkString("\n") }

  file foreach {
    text => log(text)
  }

  file.failed foreach {
    case fnfe: java.io.FileNotFoundException => log(s"Cannot find file - $fnfe")
    case t => log(s"Failed due to $t")
  }

  import scala.util.{Try, Success, Failure}

  file onComplete {
    case Success(text) => log(text)
    case Failure(t) => log(s"Failed due to $t")
  }

}