package day1

import java.text.NumberFormat

import cats.kernel.Monoid

import scala.annotation.{implicitNotFound, tailrec}

object Functions {
  //Understanding functions in scala (functions are objects) ans SAM (Single Abstract Method)
  //lambdas to begin with
  /*
  * interface Show<T> {
  *   String show(T t);
  * }
  * */
  trait Show[T] {
    def show(t:T):String
  }
  val intShow1 = new Show[Int] {
    override def show(t: Int): String = t.toString
  }
  val intShow2: Show[Int] = (t: Int) => t.toString

  val func1:Function1[Int,Int] = new Function1[Int,Int] {
    override def apply(v1: Int): Int = 2*v1
  }
  val func_1:Int => Int = v1 => 2 * v1

  val func2:Function2[Int,Int,Int] = new Function2[Int,Int,Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  val func_2:(Int, Int) => Int = (v1, v2) => v1 + v2

  //Functions vs Methods and Eta expansion (https://stackoverflow.com/questions/39445018/what-is-the-eta-expansion-in-scala)
  val f:Int => Int = x => x * 2 //function
  val duplicate_f: Int => Int = f
  def ff(x:Int):Int = x * 2  //method
  val f_from_ff1 = ff _
  val f_from_ff2:Int => Int = ff

  //Function curry
  //f:a -> b ; f:(a,b) -> c ; f:a -> b -> c ; f:a -> b -> c -> d
  /*
  def parseXML(dom:DOM)(f:parses The dom) = ???
  val injectedDom = parseXML(XMLBookDOM)
  val extractedAuthorNames = injectedDom(extractName)
  val extractedBooksByAuthor = injectedDom(extractBook)
  */
  val adder:Int => Int => Int = x => y => x + y
  val adderOf2: Int => Int = adder(2) // partial application
  def adderDef(x:Int)(y:Int):Int = x + y
  val addOf2Def: Int => Int = adderDef(2)

  //curry help for eta expansion

  //why curry https://softwareengineering.stackexchange.com/questions/185585/what-is-the-advantage-of-currying

  //important benefit of curry is writing abstract functions and also specialize where required

  //curry also helps to do higher order functions

  //Curry helps to do partial application

  //interesting usage of curry in Currying file

  //More on functions, store in data structure an example

  //Passing by value and passing by name in scala methods, let's make code a bit lazy

  //Partial Functions a big no

  //Basic Parametric polymorphic functions
  //How many possible implementation are valid for the below function
  def add(v1: Int, v2: Int): Int = ???

  //How many possible implementation are valid for the below function
  def addP[P](v1: P, v2: P): P = ???

  //Function composition (compose, andThen, tap, and pipe)
  //f: a -> b

  //g: b -> c
  //  g compose f = b -> c after a -> b = a -> c

  //f andThen g =   a -> b andThen b -> c  = a -> c

  //A simple case for pipe
  //consider the following functions already defined
  type Amount = Double
  sealed trait Tax {
    val formulae:Amount => Amount
  }
  object Tax {
    case class VAT(formulae:Amount => Amount = _ * (3D / 100)) extends Tax
    case class ServiceTax(formulae:Amount => Amount = _ * (2D / 100)) extends Tax
    case class ServiceCharge(formulae:Amount => Amount) extends Tax

    import scala.util.chaining._
    val addTaxToTotal:Tax => Amount => Amount =
      tax => amount => tax.formulae(amount) + amount
    //{value}.pipe(f:value => {secondValue}).pipe(f:secondValue => {})
    def addTaxOnBill(totalAmount:Amount):Amount = ???

    def addTaxOnBillWithDebug(totalAmount:Amount):Amount = ???

  }
  //different construct used with function; type, trait and case class
  type MyFunc[A,B] = A => B
  trait MyFunction1[A,B] extends (A => B)
  case class FunctionWrapper[A,B](f: A => B)

  /*
  Passing and returning function to and from a function, talking in terms of function
  HOF and combinators
  */

  //practicing HoF which parametric polymorphic
  def tupled[T1, T2, R](f: (T1, T2) => R): ((T1, T2)) => R = ???

  def untupled[T1, T2, R](f: ((T1, T2)) => R): (T1, T2) => R = ???
  def makeCurried[A,B,C](f:(A,B) => C): A => B => C = ???
  def uncurry[A,B,C](f:A => B => C): (A, B) => C = ???
  def flipArgs[A,B,C](f:A => B => C): B => A => C = ???
  def fanOut[C, A, B](fst: C => A, snd: C => B): C => (A, B) = ???
  def fanIn[C, A, B](h: C => (A, B)): (C => A, C => B) = ???
  def bimap[A, A1, B, B1](f: A => A1, g: B => B1): ((A, B)) => (A1, B1) = ???
  def either[C, A, B](f: A => C, g: B => C): Either[A, B] => C = ???
  def chain[T](fs:Seq[T => T]):T => T = ???

  //Can we convert any method which is not curred to a curried function
  //if yes please write the implementation

  /*
    Exercise 1
  */
  sealed trait Comp
  case object LT extends Comp
  case object GT extends Comp
  case object EQ extends Comp

  def bigger[A](v1: A, v2: A)(comparator:(A,A) => Comp):A = ???
  def smaller[A](v1: A, v2: A)(comparator:(A,A) => Comp):A = ???

  //write some test calls for bigger and smaller

  //Pure functions & side effects introduction
  /*
  * 1. It always returns a value
  *   a. Always returns same value for the same input
  * 2. No side effect (throwing exception, changing something which is global, latency, I/O, etc)
  *   b. It manages effect
  *
  * Why pure functions
  * */

  //Simple recursion examples along with a recursive data structure
  sealed trait MyList[A]

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
  def flatten[A, B](l:List[A])(implicit ev: A <:< List[B]):List[B] = ???

  //introduction to map and flatmap


  /*A bit of implicits*/
  //https://docs.scala-lang.org/tutorials/FAQ/finding-implicits.html

  /*
  * 1. implicit parameter
  * 2. implicit conversion, not advised to be used
  * 4. Implicit class, extension methods
  * 3. context bounds, we will also see in type classes
  * 5. Implicit errors annotation @implicitNotFound
  * */

  object parameter {

  }
  object implicitParameter {

  }
  object implicitConversion {

  }
  object extensionMethods {

  }
}
