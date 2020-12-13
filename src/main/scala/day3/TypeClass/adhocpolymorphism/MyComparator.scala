package day3.TypeClass.adhocpolymorphism

import day3.TypeClass.subtypingpolymorphism.Company

trait MyComparator[T] {
  def compare(t1: T, t2: T): Int

  //Laws the types need to obey
  //https://hackage.haskell.org/package/base-4.14.0.0/docs/Prelude.html#t:Ord
  def transitivity(edgeCase:T => T => T => Boolean):T => T => T => Boolean = x => y => z => {
    if(!edgeCase(x)(y)(z)) true
    else ((compare(x, y) == (-1)) && (compare(y, z) == (-1))) && (compare(x , z) == (-1))
  }

  def reflexivity:T => Boolean = x => compare(x, x) == 0

  def antisymmetry:T => T => Boolean = x => y =>
    compare(x, y) == 0 && compare(x, y) == compare(y, x)

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
