package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

trait MyComparator[T] {
  def compare(t1: T, t2: T): Int
}

object MyComparator {
  def compare[T](t1: T, t2:T)(implicit comparator: MyComparator[T]): Int = comparator.compare(t1, t2)
//  def compare[T : MyComparator](t1: T, t2:T): Int = implicitly[MyComparator[T]].compare(t1, t2)

//  def apply[T](implicit comparator: MyComparator[T]): MyComparator[T] = comparator
//  def compare[T : MyComparator](t1: T, t2:T): Int = MyComparator[T].compare(t1, t2)

  implicit val comparatorCompany: MyComparator[Company] = (t1: Company, t2: Company) => {
    if(t1.strength < t2.strength) -1
    else if(t1.strength > t2.strength) 1
    else 0
  }
}
