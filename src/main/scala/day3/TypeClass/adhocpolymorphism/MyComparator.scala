package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

trait MyComparator[T] {
  def compare(t1: T, t2: T): Int
}

object MyComparator {
  implicit val comparatorCompany: MyComparator[Company] = (t1: Company, t2: Company) => {
    if(t1.strength < t2.strength) -1
    else if(t1.strength > t2.strength) 1
    else 0
  }

}
