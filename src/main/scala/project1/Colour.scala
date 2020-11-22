package project1

sealed trait Colour
object Colour {
  case object Red extends Colour
  case object Green extends Colour
  case object Blue extends Colour
  case object NoColour extends Colour
}

