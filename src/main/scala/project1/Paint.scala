package project1

import project1.Figure._
import project1.Shape._

import scala.util.chaining._

case class Paint[A](draw:Shape => (A,Shape)) {
  def result[B]:B => Paint[B] = ???
  def map[B](f:A => B):Paint[B] = ???
  def flatMap[B](f:A => Paint[B]):Paint[B] = ???
}

object Paint extends App {
  def paintIt(shape: Shape): Paint[Shape] = Paint(canvas => {
    ???
  })

  val program: Paint[Shape] = for {
    _ <- paintIt(square(Coordinate(13,14))(Square(12)))
    s <- paintIt(rectangle(Coordinate(20,24))(Rectangle(12,13)))
  } yield s

  program.
    draw(makeCanvas(100,100))._2
    .pipe(prettyPrint)
    .pipe(println)
}
