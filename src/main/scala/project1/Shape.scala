package project1

import project1.Cell._

import scala.annotation.tailrec
import scala.util.chaining._

/**
  * <p>Shape is a a simple product type with information about a specific shape
  * <p>Shape represent a function which take a origin point and draws the respective shape accordingly
  * <p>For example, for a rectangle and a square we assume left top is the origin point and draw accordingly
  * {{{
  *    val length = 10
  *    val breadth = 15
  *    val rect:Shape[Rectangle] = rectangle(length, breadth) // returns a shape
  *    //and not you can pass the origin and rectangle will draw the shape accordingly on the respective coordinates
  * }}}
  *
  *
  * */
sealed trait Figure
object Figure {
  case class Rectangle(length: Int, breadth: Int) extends Figure
  case class Square(side: Int) extends Figure
  case class Circle(radius: Int) extends Figure
  case object EmptyFigure extends Figure
}

case class Shape(originPoint: Coordinate, cells: List[Cell])

object Shape {
  import project1.Figure._

  type Canvas = Shape

  /**
    *{{{
    * Example width * height = 6 * 4 canvas
    * (0,0) (0,1) (0,2) (0,3) (0,4) (0,5)
    * (1,0) (1,1) (1,2) (1,3) (1,4) (1,5)
    * (2,0) (2,1) (2,2) (2,3) (2,4) (2,5)
    * (3,0) (3,1) (3,2) (3,3) (3,4) (3,5)
    * }}}
    * */
  def makeCanvas(height: Int, width: Int): Canvas =
    (for {
      b <- 0 to width
      l <- 0 to height
    } yield EmptyCell(Coordinate(b, l))).toList.pipe(Shape(Coordinate(0, 0), _))

  def rectangle(originPoint: Coordinate): Rectangle => Shape =
    r => {
      (for {
        b <- originPoint.x to (originPoint.x + r.length)
        l <- originPoint.y to (originPoint.y + r.breadth)
      } yield OccupiedCell(Coordinate(b, l), Colour.NoColour)).toList
        .pipe(Shape(originPoint, _))
    }

  def square(originPoint: Coordinate): Square => Shape =
    s => rectangle(originPoint)(Rectangle(s.side, s.side))

  def circle[T](originPoint: Coordinate)(radius: Int): Shape = ???

  def prettyPrint: Shape => String =
    s => {
      @tailrec
      def loop(prevRow: Int, acc: String, cells: List[Cell]): String =
        cells match {
          case List() => acc
          case OccupiedCell(Coordinate(row, _), _) :: xs if prevRow != row =>
            loop(row, acc + "\n" + ".", xs)
          case OccupiedCell(Coordinate(row, _), _) :: xs =>
            loop(row, acc + ".", xs)
          case EmptyCell(Coordinate(row, _)) :: xs if prevRow != row =>
            loop(row, acc + "\n" + " ", xs)
          case EmptyCell(Coordinate(row, _)) :: xs => loop(row, acc + " ", xs)
        }
      loop(0, "", s.cells)
    }
}
