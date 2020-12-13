package day3.TypeClass

import day1.Functions.Comp

// Use the abstraction of behavior in a functionality
object MyCollections {
  def sort[A <: MyComparable[A]](collection: List[A]): List[A] = ???

  def sort2[A](comparator: MyComparator[A], collection: List[A]): List[A] = ???

}



// Add the behavior to your type
case class Person(name: String, age: Int) extends MyComparable[Person] {
  override def compare(t: Person): Int = ???
}

// Problems
// The behavior definition is now coupled to the type. What if we have to change the logic of comparison?
// What if we have to add that behavior to an external type

trait MyComparator[T] {
  def compareTo(t1: T, t2: T): Int
}

object MyComparator {
  implicit val personComparator4 = new MyComparator[Person] {
    override def compareTo(t1: Person, t2: Person): Int = ???
  }

  implicit val personComparator41 = new MyComparator[Int] {
    override def compareTo(t1: Int, t2: Int): Int = ???
  }
}

trait MyComparatorInstances {
  implicit val personComparator3 = new MyComparator[Person] {
    override def compareTo(t1: Person, t2: Person): Int = ???
  }

}

object Person {
//  implicit val personComparator = new MyComparator[Person] {
//    override def compareTo(t1: Person, t2: Person): Int = ???
//  }
}

object MyCollections2 {

  def sort[A](comparable: MyComparable[A], collection: List[A]): List[A] = ???


//  sort2(Person.personComparator, List.empty)

  def sort3[A: MyComparator](collection: List[A]): List[A] = ???

  sort3(List.empty[Person])
}



