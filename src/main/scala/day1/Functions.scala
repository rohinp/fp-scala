package day1

object Functions {
  //Understanding functions in scala (functions are objects)
  //lambdas to begin with

  //Functions vs Methods and Eta expansion (https://stackoverflow.com/questions/39445018/what-is-the-eta-expansion-in-scala)

  //Passing by value and passing by name in scala methods, let's make code a bit lazy

  //Function curry

  //Partial Application

  //Partial Functions a big no

  //Function composition

  //Basic Parametric polymorphic functions

  /*
  Passing and returning function to and from a function, talking in terms of function
  HOF and combinators
  */
  def show[A](a:A)(howToShow:A => String):String = ???

  def compose[A,B,C](f:B => C, g:A => B): A => C = ???
  def andThen[A,B,C](f:A => B, g:B => C): A => C = ???

  /*
    Exercise 1
  */
  sealed trait Comp
  case object LT extends Comp
  case object GT extends Comp
  case object EQ extends Comp

  def bigger[A](v1: A, v2: A)(comparator:(A,A) => Comp):A = ???
  def smaller[A](v1: A, v2: A)(comparator:(A,A) => Comp):A = ???

  def makeCurried[A,B,C](f:(A,B) => C): A => B => C = ???
  def uncurry[A,B,C](f:A => B => C): (A, B) => C = ???
  def flipArgs[A,B,C](f:A => B => C): B => A => C = ???

  //Pure functions & side effects

  //Simple recursion examples along with a recursive data structure
  sealed trait MyList[A]

  def head[A](list: MyList[A]):Option[A] = ???
  def tail[A](list: MyList[A]):Option[MyList[A]] = ???

  //pardon the existential type here (https://blog.knoldus.com/back2basics-existential-types-in-scala/)
  def length(list: MyList[A] forSome {type A}):Int = ???

  /*
  * Exercise 2
  * implement method take for list
  * */
  def take[A](i:Int)(list: MyList[A]):MyList[A] = ???

  /*
  * Exercise 3
  * implement method map for the list
  * */
  def map[A,B](f:A => B)(list: MyList[A]):MyList[B] = ???

  /*
  * Exercise 4
  * implement a method fold on scala list
  * */
  def fold[A,B](list: List[A])(zero:B)(f:(A, B) => B):List[B] = ???

  /*
  * Exercise 5
  * implement a method flatten to flatten a list
  *
  * introducing generalised types so early just to give you a taste of scala
  * https://blog.bruchez.name/2015/11/generalized-type-constraints-in-scala.html
  * */
  def flatten[A](l:List[A])(implicit ev: A <:< List[A]):List[A] = ???

}
