package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

object Sorting7 {
  def sort[A : MyComparator](collection: List[A]): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t))
  }

  private def insert[A : MyComparator](elem: A, collection: List[A]): List[A] = {
    val comparator = implicitly[MyComparator[A]]
    collection match {
      case Nil => List(elem)
      case h :: t if comparator.compare(elem, h) < 0 => h :: collection
      case h :: t => h :: insert(elem, t)
    }
  }
}

object Sorting7_helper {

  val listCompanies: List[Company] = ???

  Sorting7.sort(listCompanies)
}

