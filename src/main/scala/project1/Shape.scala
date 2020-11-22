package project1

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

case class Shape(
    figure: Figure,
    originPoint: Coordinate,
    cells: List[Cell[ Nothing ]] //need to update
)

object Shape {
  type Canvas = Shape

  def makeCanvas(height: Int, width: Int): Canvas = {
    val originPoint: Coordinate = Coordinate(0,0)
    ???
  }

  def rectangle(originPoint: Coordinate)(length: Int, bredth: Int): Shape = ???
  def square(originPoint: Coordinate)(side: Int): Shape = ???
  def circle(originPoint: Coordinate)(radius: Int): Shape = ???

  def prettyPrint:Shape => String = ???
}
