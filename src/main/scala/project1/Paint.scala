package project1

import project1.Figure._
import project1.Shape._

import scala.util.chaining._

case class Paint(draw:Shape => Shape) {
  def result:Shape => Paint = s => Paint(_ => s)
  def map(f:Shape => Shape):Paint = flatMap(result compose draw)
  def flatMap(f:Shape => Paint):Paint =
    Paint(s => (f compose draw)(s).draw(s))
}

object Paint extends App {
  def paintIt(shape: Shape): Paint = Paint(canvas => {
    ???
  })

  val program: Paint = for {
    _ <- paintIt(square(Coordinate(13,14))(Square(12)))
    s <- paintIt(rectangle(Coordinate(20,24))(Rectangle(12,13)))
  } yield s

  program.
    draw(makeCanvas(100,100))
    .pipe(prettyPrint)
    .pipe(println)
}
