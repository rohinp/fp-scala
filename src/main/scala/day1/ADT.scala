package day1

object ADT extends App {
  //What is a Type
  //What is a Value

  //A type with empty set
  val x:Nothing = ???
  //A type with singleton set
  val u:Unit = ()
  //Tuples in scala
  val t = (1,"rr")
  //basic scala constructs use to represent a type {class, case class, trait, object, case object, sealed trait} and type alias

  //What is ADT? (Algebraic data type)
  //https://about.chatroulette.com/posts/algebraic-data-types/

  //Sum type

  //product type

  //domain modeling using ADT
  //IoT Domain, representing devices communicating with Http and others with MQtt
  sealed trait IoTProtocol

  //One more example, Shipping types
  sealed trait ShippingType

  /*
  Sum type & Product type
  Option
  Either
  */

  /*-All exercises below are assumed to be solved only using sealed trait , case classes and case objects-*/
  /**
   * Exercise 1
   * Data model of a credit card
   */

  /*
  * Exercise 2
  * Data model of a Paint Application
  * */

  //lets do some pattern matching
  //https://docs.scala-lang.org/tour/pattern-matching.html
  //https://docs.scala-lang.org/overviews/scala-book/match-expressions.html
  //Wild card or otherwise conditions
  //guards in pattern matching (if)
  //Handling alternate cases(|)
  //back ticks and capital letters in pattern match https://users.scala-lang.org/t/upper-case-letters-in-pattern-matching/5947/2
  //Alias in pattern matching
  //sealed traits and match errors in pattern matching
  // For more details check here https://scala-lang.org/files/archive/spec/2.12/08-pattern-matching.html


  //Using the solution from exercise 1

  //Lets construct some objects using smart constructors

  /*
  * Exercise 4
  * Pattern match on an expression to print what type of value it returns
  * */

  /*
  * Exercise 5
  * Pattern matching with regular expression
  * Note : uncomment and implement
  * */
  val date = raw"""(\d{4})-(\d{2})-(\d{2})""".r

  /*"2004-01-20" match {
    case ??? => "It's a date!"
  }*/

  /*"2004-01-20" match {
    case ??? => ??? //s"$year was a good year for PLs."
  }*/

  /*
  * Exercise 6
  * Pattern match on an Option inside either
  * */

  type Input = Either[String, Option[String]]
  /*def getStringFrom(input:Input) : String = input match {
    case ??? => "implement this method"
  }*/

  /*
  * Exercise 7
  * Use Pattern match on tuple for below result
  * if _1 == _2 then Option(_1 + _2)
  * if _1 < _2 then Option(_1)
  * else None
  * */
  def computeTuple(t:(Int,Int)):Option[Int] = ???


}
