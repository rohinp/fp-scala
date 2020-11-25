package project1

import project1.Figure._
import project1.Shape._

import scala.util.chaining._

case class Paint[A](draw:Shape => (A,Shape)) {
  def result[B]:B => Paint[B] = b => Paint(s => (b,s))
  def map[B](f:A => B):Paint[B] = flatMap(shape => result(f(shape)))
  def flatMap[B](f:A => Paint[B]):Paint[B] = Paint(shape => {
    val (a, shape1) = draw(shape)
    f(a).draw(shape1)
  })
}

object Paint extends App {
  def paintIt(shape: Shape): Paint[Shape] = Paint(canvas => {
    (shape, canvas.copy(cells = canvas.cells.foldLeft((List.empty[Cell], shape.cells)){
      (acc, cell) => {
        acc match {
          case (c ,Nil) => (c.appended(cell), Nil)
          case (c, x::xs) if x.coordinate == cell.coordinate => (c.appended(x), xs)
          case (c, xs)  => (c.appended(cell), xs)
        }
      }
    }._1))
  })

  val program: Paint[Shape] = for {
    _ <- paintIt(square(Coordinate(5,10))(Square(10)))
    s <- paintIt(rectangle(Coordinate(20,30))(Rectangle(20,10)))
  } yield s

  program.
    draw(makeCanvas(100,100))._2
    .pipe(prettyPrint)
    .pipe(println)
}
