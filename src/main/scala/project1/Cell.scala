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
sealed trait Cell {
  val coordinate: Coordinate
  val colour: Colour
}

object Cell {
  case class OccupiedCell(coordinate: Coordinate, colour: Colour) extends Cell
  case class EmptyCell(coordinate: Coordinate) extends Cell {
    override val colour: Colour = Colour.NoColour
  }

  def emptyCell(coordinate: Coordinate):Cell = EmptyCell(coordinate)

}
