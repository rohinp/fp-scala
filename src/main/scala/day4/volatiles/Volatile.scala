package day4.volatiles
import day4.basics.Utilities._

object Volatile extends App {
  case class Page(txt: String, var position: Int)

  val pages = for (i <- 1 to 5) yield
    Page("Na" * (100 - 20 * i) + " Batman!", -1)

  @volatile var found = false

  for (p <- pages) yield thread {
    log(p.txt)
    var i = 0
    while (i < p.txt.length && !found) {
      if (p.txt(i) == '!') {
        p.position = i
        found = true
      } else i += 1
    }
  }

  while (!found) {}

  log(s"results: ${pages.map(_.position)}")
}