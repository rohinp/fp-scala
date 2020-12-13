package day3.TypeClass

class Sorting {
  def sort[A](collection: List[A]): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t))
  }

  private def insert[A](elem: A, collection: List[A]): List[A] = collection match {
    case Nil => List(elem)
//    case h :: t if elem <= h => h :: collection
    case h :: t => h :: insert(elem, t)
  }
}
