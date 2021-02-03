package day4.futures

import scala.util.{Failure, Success}

object FuturesCallbacks extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source
  import day4.basics.Utilities._

  def getUrlSpec: Future[Seq[String]] = Future {
    val f = Source.fromURL("http://www.w3.org/Addressing/URL/url-spec.txt")
    try f.getLines.toList finally f.close()
  }

  //here the future started async
  val urlSpec: Future[Seq[String]] = getUrlSpec

  def find(lines: Seq[String], word: String) = lines.zipWithIndex collect {
    case (line, n) if line.contains(word) => (n, line)
  } mkString "\n"

  urlSpec foreach {
    lines => log(s"Found occurrences of 'telnet'\n${find(lines, "telnet")}\n")
  }

  urlSpec foreach {
    lines => log(s"Found occurrences of 'password'\n${find(lines, "password")}\n")
  }

  urlSpec.failed foreach {
    case t => log(s"exception occurred $t")
  }

  log("callbacks installed, continuing with other work")
  log(urlSpec.isCompleted + " before while")
  while(!urlSpec.isCompleted){}
  log(urlSpec.isCompleted + " ")
  log(urlSpec.value.toString)

}