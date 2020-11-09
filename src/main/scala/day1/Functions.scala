package day1

import java.text.NumberFormat

object Functions {
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

  //interesting usage of curry in Currying file

  //Eta expansion
  List(f _ , g _)
  List[Int => Int](f, g)

  //Passing by value and passing by name in scala methods, let's make code a bit lazy

  //Partial Functions a big no

  //Basic Parametric polymorphic functions
  //How many possible implementation are valid for the below function
  def add(v1: Int, v2: Int): Int = ???

  //How many possible implementation are valid for the below function
  def addP[P](v1: P, v2: P): P = ???

  //Function composition (compose, andThen, tap, and pipe)
  //f: a -> b
  def headO1(l:List[Int]): Option[Int] = l.headOption
  def headO:List[Int] => Option[Int] = _.headOption
  //g: b -> c
  def makeString1(op:Option[Int]): String = op.fold(s"It didn't worked")(in => s"This is my number $in")
  def makeString:Option[Int] => String = _.fold(s"It didn't worked")(in => s"This is my number $in")
  //  g compose f = b -> c after a -> b = a -> c
  def headMake00(l: List[Int]):String = makeString1(headO1(l))
  val headMake0: List[Int] => String = l => makeString(headO(l))

  def headMake11(l:List[Int]): String = (makeString1 _).compose(headO1)(l)
  val headMake1: List[Int] => String = makeString.compose(headO)
  //f andThen g =   a -> b andThen b -> c  = a -> c
  def headMake22(l:List[Int]): String = (headO1 _).andThen(makeString1)(l)
  val headMake2: List[Int] => String = headO.andThen(makeString)

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
    val addTaxToTotal:Tax => Amount => Amount = tax => amount => tax.formulae(amount) + amount

    def addTaxOnBill(totalAmount:Amount):Amount = ???

    def addTaxOnBillWithDebug(totalAmount:Amount):Amount = ???

  }
  //different construct used with function; type, trait and case class

  /*
  Passing and returning function to and from a function, talking in terms of function
  HOF and combinators
  */
  def show[A](a:A)(howToShow:A => String):String = ???

  def compose[A,B,C](g:B => C, f:A => B): A => C = a => g(f(a))
  def andThen[A,B,C](f:A => B, g:B => C): A => C = a => g(f(a))

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
  *   b. It manages side effect
  *
  * Why pure functions
  * */

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
  * 4. Implicit class
  * 5. Implicit errors annotation @implicitNotFound
  * */
}
