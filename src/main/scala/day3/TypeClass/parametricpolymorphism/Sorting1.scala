package day3.TypeClass.parametricpolymorphism

object Sorting1 {
  def sort[A](collection: List[A]): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t))
  }

  private def insert[A](elem: A, collection: List[A]): List[A] = {
    val comparator = new MyGenericComparator[A]
    collection match {
      case Nil => List(elem)
      case h :: t if comparator.compare(elem, h) < 0 => h :: collection
      case h :: t => h :: insert(elem, t)
    }
  }
}
