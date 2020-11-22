package project1

/**
  * <P>The most basic element of the paint application.
  * <UL>
  *  <li>Everything is built from cell
  *  <li>Shapes are built of cells
  *  <li>Shapes are drawn on canvas and again Canvas are built of empty cells
  *  <li>Every Cell have a coordinate and colour
  *  <li>Operators/behaviours for cell can go into Cell
  *  <li>Constructors for cell can go into Cell companion
  *  <li>Feel free to choose if you want Cell to be a case class or trait or any other construct suitable
  * */
sealed trait Cell[T] {
  val coordinate: Coordinate
  val colour: Colour
  val data:T
}

object Cell {
  case class OccupiedCell[T](coordinate: Coordinate, colour: Colour, data:T) extends Cell[T]
  case class EmptyCell(coordinate: Coordinate) extends Cell[Unit] {
    override val colour: Colour = Colour.NoColour
    override val data: Unit = ()
  }

  def toOccupied[T]:Cell[T] => Cell[T] = ???
}
