package day3

object TypeClass {
  //Ad-hoc polymorphism and OCP
  //https://www.cse.iitk.ac.in/users/karkare/oldcourses/2010/cs653/Papers/ad-hoc-polymorphism.pdf

  /**
   * <p><b>Parametric polymorphism</b> occurs when a function
   * <p>is defined over a <b>range of types</b>,
   * <p>acting in the same way for each type.
   * <p> A typical example is the length function,
   * <p>which acts in the same way on a list of integers and a list of floating point numbers
   * <br/><br/>
   * <p><b>Ad-hoc</b> polymorphism occurs when a function
   * <p>is defined over <b>several different types</b>,
   * <p>acting in a different way for each type.
   * <p>A typical example is overloaded multiplication:
   * <p>the same symbol may be used to denote multiplication of integers (as in 3*3)
   * <p>and multiplication of floating point values (as in 3.14*3.14)
  * */



  //Things required to implement the pattern in scala
  /*
   * 1. Behaviour
   * 2. Laws/properties
   * 3. instance on a Type
   * 4. interface methods
   * */

  /*
   *  Exercise 1
   *  Create a type class PrettyPrint, which prints any type in a fancy way.
   * */

  /*
   * Exercise 2
   * Create a type class Enum
   * */
  trait Enum[T]
}
