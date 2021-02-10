package day4.futures

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source
import scala.concurrent.Future
import scala.util.Using
import day4.basics.Utilities._
object FutureSequencing extends App {
  val urls = List("http://www.ietf.org/rfc/rfc1855.txt", "http://www.w3.org/Addressing/URL/url-spec.txt", "http://www.w3.org/Addressing/URL/funnyurl.test")


  log("Done")
}
