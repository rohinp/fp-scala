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

case class Shape(cells: List[Cell])

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
      cell = if(border(width,height)(Coordinate(b,l))) OccupiedCell(Coordinate(b, l),Colour.NoColour) else EmptyCell(Coordinate(b, l))
    } yield cell).toList.pipe(Shape(_))

  def border(width:Int, height:Int)(coordinate: Coordinate):Boolean =
    List[Coordinate => Boolean](_.x == 0, _.y == 0, _.y == width, _.x == height)
      .exists(_ (coordinate))

  def rectangle(originPoint: Coordinate): Rectangle => Shape =
    r => {
      (for {
        b <- originPoint.x.toInt to (originPoint.x + r.length).toInt
        l <- originPoint.y.toInt to (originPoint.y + r.breadth).toInt
      } yield OccupiedCell(Coordinate(b, l), Colour.NoColour)).toList
        .pipe(Shape(_))
    }

  def square(originPoint: Coordinate): Square => Shape =
    s => rectangle(originPoint)(Rectangle(s.side, s.side))

  def circle[T](originPoint: Coordinate):Circle => Shape = circle =>
    (for {
      degree <- 0 to 360
      x = circle.radius * Math.cos(degree) + originPoint.x
      y = circle.radius * Math.sin(degree) + originPoint.y
    } yield OccupiedCell(Coordinate(x.toInt, y.toInt), Colour.NoColour)).toList.pipe(Shape(_))

  def prettyPrint: Shape => String =
    s => {
      @tailrec
      def loop(prevRow: Double, acc: String, cells: List[Cell]): String =
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

  def combine:Shape => Shape => Shape = s1 => s2 => {
    s2.cells.foldLeft(s1.cells){
      (acc, cell) => {
        acc.map(e => if(e.coordinate == cell.coordinate) Cell.combine(e)(cell) else e)
      }
    }.pipe(Shape.apply)
  }
}
