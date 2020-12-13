package day3.TypeClass.subtypingpolymorphism

object Sorting2 {
  def sort[A <: MyComparable[A]](collection: List[A]): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t))
  }

  private def insert[A <: MyComparable[A]](elem: A, collection: List[A]): List[A] = {
    collection match {
      case Nil => List(elem)
      case h :: t if elem.compare(h) < 0 => h :: collection
      case h :: t => h :: insert(elem, t)
    }
  }
}
