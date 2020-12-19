package day1

import scala.Option
import scala.language.reflectiveCalls

object CreatingTypes {
  //Class in scala

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

   //syntactic sugar for apply

  /*
  1. apply
  2. unapply
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

  //also we can have some abstract behaviour behaviour

  //we can also have sealed traits

  //Enumeration in scala
  // Define a new enumeration with a type alias and work with the full set of enumerated values
  //https://www.scala-lang.org/api/current/scala/Enumeration.html
  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  import WeekDay._

  def isWorkingDay(d: WeekDay): Boolean = !(d == Sat || d == Sun)

  //structural type
}
