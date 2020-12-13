package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

object Sorting6 {
  def sort[A : MyComparator](collection: List[A]): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t))
  }

  private def insert[A : MyComparator](elem: A, collection: List[A]): List[A] = {
    collection match {
      case Nil => List(elem)
      case h :: t if MyComparator.compare(elem, h) < 0 => h :: collection
      case h :: t => h :: insert(elem, t)
    }
  }
}

object Sorting6_helper {

  val listCompanies: List[Company] = ???

  Sorting6.sort(listCompanies)
}

