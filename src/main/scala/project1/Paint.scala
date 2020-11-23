package project1

import project1.Shape._
import scala.util.chaining._

case class Paint[S](draw:S => S) {
  def map[T](f:S => T):Paint[T] = ???
  def flatMap[T](f:S => Paint[T]):Paint[T] = ???
}

object Paint extends App {
  def paintIt(shape: Shape[String]): Paint[Shape[String]] = Paint(canvas => {
    ???
  })

  val program: Paint[Shape[String]] = for {
    _ <- paintIt(square(Coordinate(13,14))(12))
    s <- paintIt(rectangle(Coordinate(20,24))(12,13))
  } yield s

  program.
    draw(makeCanvas(100,100))
    .pipe(prettyPrint(stringFormat))
    .pipe(println)
}
