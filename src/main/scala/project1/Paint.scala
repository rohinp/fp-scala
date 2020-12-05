package project1

import project1.Figure._
import project1.Shape._
import scala.util.chaining._

case class Paint[A](draw: Shape => (A, Shape)) {
  def result[B]: B => Paint[B] = b => Paint(s => (b, s))
  def map[B](f: A => B): Paint[B] = flatMap(shape => result(f(shape)))
  def flatMap[B](f: A => Paint[B]): Paint[B] =
    Paint(shape => {
      val (a, shape1) = draw(shape)
      f(a).draw(shape1)
    })
}

object Paint extends App {
  def paintIt(shape: Shape): Paint[Shape] =
    Paint(canvas => (shape, Shape.combine(canvas)(shape)))

  def get: Paint[Shape] = Paint(p => (p, p))

  val program: Paint[Shape] = for {
    _ <- paintIt(square(Coordinate(5, 5))(Square(3)))
    _ <- paintIt(rectangle(Coordinate(10, 10))(Rectangle(20, 10)))
    _ <- paintIt(circle(Coordinate(40, 40))(Circle(15)))
    s <- get
  } yield s

  program
    .draw(makeCanvas(100, 100))
    ._2
    .pipe(prettyPrint)
    .pipe(println)
}
