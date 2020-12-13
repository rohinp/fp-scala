package day3.TypeClass.subtypingpolymorphism

// Create a new abstract behavior
trait MyComparable[T] {
  def compare(t: T): Int
}


