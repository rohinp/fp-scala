package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

object Sorting4 {
  def sort[A](collection: List[A])(implicit compare: (A, A) => Int): List[A] = collection match {
    case Nil => Nil
    case h :: t => insert(h, sort(t))
  }

  private def insert[A](elem: A, collection: List[A])(implicit compare: (A, A) => Int): List[A] = {
    collection match {
      case Nil => List(elem)
      case h :: t if compare(elem, h) < 0 => h :: collection
      case h :: t => h :: insert(elem, t)
    }
  }
}

object Sorting4_helper {
  implicit val compareCompany: (Company, Company) => Int = (c1, c2) => {
    if(c1.strength < c2.strength) -1
    else if(c1.strength > c2.strength) 1
    else 0
  }

  val listCompanies: List[Company] = ???

  Sorting4.sort(listCompanies)
}

