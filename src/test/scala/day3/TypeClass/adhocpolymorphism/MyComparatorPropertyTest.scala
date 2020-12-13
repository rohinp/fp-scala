package day3.TypeClass.adhocpolymorphism

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

class MyComparatorPropertyTest extends Properties("Comparator") {

  val myIntComparator: MyComparator[Int] =
    (t1: Int, t2: Int) => if (t1 > t2) 1 else if (t1 < t2) -1 else 0

  val wrongComparator: MyComparator[Int] =
    (t1: Int, t2: Int) => t1 + t2

  def pos:Int => Boolean = _ >= 0

  property("transitivity") = forAll((f: Int) => {
    myIntComparator.transitivity(x => y => z => pos(x) ==  pos(y) && pos(x) == pos(z))(f)(f + 1)(f + 2)
  })
  property("antisymmetry") = forAll((f:Int) => myIntComparator.antisymmetry(f)(f))
  property("reflexivity") = forAll((f: Int) => myIntComparator.reflexivity(f))

  //these will fails as wrong implementation of comparator
  property("transitivityWrong") = forAll((f: Int) => {
    wrongComparator.transitivity(x => y => z => pos(x) ==  pos(y) == pos(z))(f)(f + 1)(f + 2)
  })
  property("antisymmetryWrong") = forAll((f: Int) => wrongComparator.antisymmetry(f)(f))
  property("reflexivityWrong") = forAll((f: Int) => wrongComparator.reflexivity(f))

}
