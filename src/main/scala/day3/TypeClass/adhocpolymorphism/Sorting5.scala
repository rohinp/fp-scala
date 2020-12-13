package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

object Sorting5 {
  def sort[A](collection: List[A])(comparator: MyComparator[A]): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t)(comparator))(comparator)
  }

  private def insert[A](elem: A, collection: List[A])(comparator: MyComparator[A]): List[A] = {
    collection match {
      case Nil => List(elem)
      case h :: t if comparator.compare(elem, h) < 0 => h :: collection
      case h :: t => h :: insert(elem, t)(comparator)
    }
  }
}

object Sorting5_helper {

  val listCompanies: List[Company] = ???

  Sorting5.sort(listCompanies)(MyComparator.comparatorCompany)
}

