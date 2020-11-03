package day1

object Functions extends App {
  //Understanding functions in scala (functions are objects) ans SAM (Single Abstract Method)
  //lambdas to begin with
  trait Show {
    def display(str:String):String
  }

  val myShow = new Show {
    override def display(str: String): String = s"Display from show $str"
  }

  val myShow1: Show = (str: String) => s"Display from show $str"

  //Functions vs Methods and Eta expansion (https://stackoverflow.com/questions/39445018/what-is-the-eta-expansion-in-scala)

  def f(i:Int):Int = i * 2
  val ff:Int => Int = (i:Int) => i * 2

  def g(g:Int):Int = g + 2
  val gg:Int => Int = (i:Int) => i + 2

  //Function curry
  def triple(x:Int, y:Int, z:Int):Int = x + y + z
  def triple1(x:Int)(y:Int)(z:Int):Int = x + y + z
  def triple2:Int => Int => Int => Int =
    x => y => z => x + y + z
  triple(1,2,3)
  val t1: Int => Int => Int = triple1(1)
  val t2: Int => Int = t1(2)
  val t2a: Int => Int = t1(3)
  val t3: Int = t2(3)

  //curry help for eta expansion
  List(triple2)

  //why curry https://softwareengineering.stackexchange.com/questions/185585/what-is-the-advantage-of-currying
  val add:Int => Int => Int = x => y => x + y
  //important benefit of curry is writing abstract functions and also specialize where required

  //curry also helps to do higher order functions
  (1 to 10).map(add(2))

  //Curry helps to do partial application

  //interesting usage of curry

  //Eta expansion
  List(f _ , g _)
  List[Int => Int](f, g)

  //Passing by value and passing by name in scala methods, let's make code a bit lazy


  //Partial Functions a big no

  //Basic Parametric polymorphic functions

  //Function composition (compose, andThen, tap, and pipe)


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

  //Pure functions & side effects introduction

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

  /*A bit of implicits*/
  //https://docs.scala-lang.org/tutorials/FAQ/finding-implicits.html

  /*
  * 1. implicit parameter
  * 2. implicit conversion
  * 3. context bounds
  * */
}
