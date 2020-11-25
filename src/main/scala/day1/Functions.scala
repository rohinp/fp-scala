package day1

import java.text.NumberFormat

import cats.kernel.Monoid

import scala.annotation.{implicitNotFound, tailrec}

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
    val addTaxToTotal:Tax => Amount => Amount =
      tax => amount => tax.formulae(amount) + amount
    //{value}.pipe(f:value => {secondValue}).pipe(f:secondValue => {})
    val addTaxOnBill_composition:Amount => Amount =
      addTaxToTotal(ServiceCharge(_ * (4D / 100))) compose addTaxToTotal(ServiceTax()) compose addTaxToTotal(VAT())

    addTaxOnBill_composition(1200D)

    val addTaxOnBill11:Amount => Amount =
      _.pipe(addTaxToTotal(VAT()))
        .pipe(addTaxToTotal(ServiceTax()))
        .pipe(addTaxToTotal(ServiceCharge(_ * (4D / 100))))

    def addTaxOnBill(totalAmount:Amount):Amount =
      totalAmount
        .pipe(addTaxToTotal(VAT()))
        .pipe(addTaxToTotal(ServiceTax()))
        .pipe(addTaxToTotal(ServiceCharge(_ * (4D / 100))))

    def addTaxOnBillWithDebug(totalAmount:Amount):Amount =
      totalAmount
        .pipe(addTaxToTotal(VAT()))
        .tap(_ => "This is not going to print...")
        .tap(a => println(s"${a} is the VAT on amount $totalAmount"))
        .pipe(addTaxToTotal(ServiceTax()))
        .tap(a => println(s"${a} is the ServiceTax on amount $totalAmount"))
        .pipe(addTaxToTotal(ServiceCharge(_ * (4D / 100))))
        .tap(a => println(s"${a} is the ServiceCharge on amount $totalAmount"))

  }
  //different construct used with function; type, trait and case class
  type MyFunc[A,B] = A => B
  trait MyFunction1[A,B] extends (A => B)
  case class FunctionWrapper[A,B](f: A => B)

  /*
  Passing and returning function to and from a function, talking in terms of function
  HOF and combinators
  */
  def show[A](a:A)(howToShow:A => String):String = howToShow(a)

  def compose[A,B,C](g:B => C, f:A => B): A => C = a => g(f(a))
  def andThen[A,B,C](f:A => B, g:B => C): A => C = a => g(f(a))

  //practicing HoF which parametric polymorphic
  def tupled[T1, T2, R](f: (T1, T2) => R): ((T1, T2)) => R =
    input => f(input._1,input._2)

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
  sealed trait MyList[+A]{ self =>
    def flatMap[B](f:A => MyList[B]):MyList[B] = ???
    def map[B](f:A => B):MyList[B] = ???
  }

  case class Cons[A](head:A, tail:MyList[A]) extends MyList[A]
  case object Empty extends MyList[Nothing]

  def head[A](list: MyList[A]):Option[A] = list match {
    case Empty => None
    case Cons(h, _) => Some(h)
  }

  def tail[A](list: MyList[A]):Option[MyList[A]] = list match {
    case Empty => None
    case Cons(_, t) => Some(t)
  }

  def length[A](list: MyList[A]):Int = {
    @tailrec
    def loop(l:MyList[A], acc:Int):Int = l match {
      case Empty => acc
      case Cons(_, t) => loop(t,acc + 1)
    }

    def loop1(l:MyList[A]):Int = l match {
      case Empty => 0
      case Cons(_, t) => 1 + loop1(t)
    }
    loop(list,0)
  }

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
    //interface functions
    def add[A](a:A, b:A)(adder:A => A => A):A =
      adder(a)(b)

    val adder1:Int => Int => Int = x => y => x + y
    add(12,13)(adder1)

    val adder2:String => String => String = x => y => x + y
    add("hello "," world")(adder2)

    case class Amount1(value:Int,decimalPart:Int)
    object Amount1{
      val adder:Amount1 => Amount1 => Amount1 =
        x => y => Amount1(x.value + y.value, x.decimalPart + y.decimalPart)
    }
    add(Amount1(12,13),Amount1(34,12))(Amount1.adder)
  }
  object implicitParameter {
    def add[A](a:A, b:A)(implicit adder:A => A => A):A =
      adder(a)(b)

    implicit val adder1:Int => Int => Int = x => y => x + y
    add(12,13)

    implicit val adder2:String => String => String = x => y => x + y
    add("hello "," world")

    case class Amount1(value:Int,decimalPart:Int)
    object Amount1{
      implicit val adder:Amount1 => Amount1 => Amount1 =
        x => y => Amount1(x.value + y.value, x.decimalPart + y.decimalPart)
    }
    add(Amount1(12,13),Amount1(34,12))
  }

  object implicitConversion {
    case class Person(name:String)
    object Person{
      implicit def personToString(person:Person):String = person.name
    }
    case class Employee(name:String, code:String)
    object Employee{
      implicit def employeeToString(emp:Employee):String = emp.name + " -- " + emp.code
    }
    def formatIt(str: String):String = s"formatted string is $str"

    formatIt(Employee("some name","123-234"))
    formatIt(Person("John"))
  }

  object extensionMethods {
    implicit class ColdSyntaxForList[A](a:List[A]) {
      def cold:List[String] = a.map(_.toString)
    }

    List(1,2,3).cold
  }


}
