package day1

import day1.ADT.ShippingType
import day1.CreatingTypes.Switchable

import scala.Option
import scala.language.reflectiveCalls

object CreatingTypes {
  //Class in scala
  class Address1(street:String, city:String, postalCode:String)
  class Address2(val street:String,val city:String = "Almere",val postalCode:String)
  object Address2{
    def makeAddress(street:String, city:String, postalCode:String):Option[Address2] = ???
  }

  val a = new Address2(street = "MyStreet",postalCode =  "1339KP")
  a.street

  //Mostly we would avoid using class and use case classes
  //We can have multiple constructors using `def this` method,
  // but we will be using smart constructors to make/create our objects
  //A simple class

  //how to create object

  //lets make the fields available by using val
  //all the fields are private
  //this wont work add1.street

  //named fields and default values assigned
  //now the fields are public and immutable

  //not recommended in FP, just to get familiar with scala syntax
  //now the fields are public and mutable

  /*
   * Class created does not have some boilerplate code which is given free when you create a case class
   * */
  case class Address11(street:String, city:String, postalCode:String)
  val aa1 = Address11.apply("s", "c", "p")
  val aa2 = Address11("s", "c", "p")
   //syntactic sugar for apply

  /*
  1. apply -- constructing an object
  2. unapply -- deconstructing an object
  3. equals and hashcode
  4. canEqual
  5. It makes the object serializable
   */


  //we can also give default values while creating class or a case class
  //We can also add access modifiers inorder to limit the access of constructors, functions, values
  //for more details on access modifiers please refer the link https://www.jesperdj.com/2016/01/08/scala-access-modifiers-and-qualifiers-in-detail/

  //Tuples can also be used to create state /data
  //Tuple is nothing but a case class with syntactic sugar and extra swap function

  // syntactic sugar for tuple

  //we can access tuple values with _{number} field names given by the tuple case class

  //object is like a factory class

  //case object is same as object except it give things mentioned below for free
  /*
   * 1. toString
   * 2. equals, hashcode, canEqual
   * 3. some product functions which we will never use
   * 4. Most important it make it serialization
   * */

  //Traits
  //As of now you can think of traits as java interface
  trait Switchable {
    val state:Boolean
    def toggle:Switchable = if(this.state) new Switchable {
      override val state: Boolean = false
    }else new Switchable {
      override val state: Boolean = true
    }
  }
  case class Bulb(state:Boolean) extends Switchable
  case class Fan(state:Boolean) extends Switchable
  case class Heater(state:Boolean) extends Switchable

  val b: Switchable = Bulb(true).toggle.toggle

  //also we can have some abstract behaviour behaviour

  //we can also have sealed traits

  //Enumeration in scala
  // Define a new enumeration with a type alias and work with the full set of enumerated values
  //https://www.scala-lang.org/api/current/scala/Enumeration.html
  object WeekDay extends Enumeration {
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  import WeekDay._

  def isWorkingDay(d: Value): Boolean = !(d == Sat || d == Sun)

  //structural type
  def test(str:String,t:{def show(str:String):String}):String = t.show(str)

  class Something{
    def show(x:String):String = s"Hello from something $x"
    def somethingElse:String = ""
  }

  test("rohin", new Something)
}
