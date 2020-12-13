package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

object Sorting9 {
  implicit class SortingOps[A](collection: List[A]) {
    def sort(implicit comparator: MyComparator[A]): List[A] = collection match {
      case Nil => Nil
      case h :: t => insert(h, t.sort)
    }

    private def insert(elem: A, collection: List[A])(implicit comparator: MyComparator[A]): List[A] = {
      collection match {
        case Nil => List(elem)
        case h :: t if MyComparator.compare(elem, h) < 0 => h :: collection
        case h :: t => h :: insert(elem, t)
      }
    }
  }
}

object Sorting9_helper {
  import Sorting9._
  val listCompanies: List[Company] = ???

  listCompanies.sort
}

