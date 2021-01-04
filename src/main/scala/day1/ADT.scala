package day1

object ADT {
  //What is a Type
  //What is a Value

  //A type with empty set
  val nothing:Nothing = ???
  //A type with singleton set
  val unit:Unit = ()

  val bool1:Boolean = true
  val bool2:Boolean = false

  //Tuples in scala
  val t2 = (1,"tst")
  t2._2
  t2._1
  val t22 = Tuple2[Int,String](1,"tst")
  val t33 = Tuple3[Int,Int,Int](1,2,3)
  val t3 = (1,2,3)
  //basic scala constructs use to represent a type {class, case class, trait, object, case object, sealed trait, Enumeration} and type alias

  //What is ADT? (Algebraic data type)
  //https://about.chatroulette.com/posts/algebraic-data-types/
  case class Employee(name:String, id:Int)

  case class MySwitches(sw1:Boolean, sw2:Boolean, sw3:Boolean)// 2 * 2 * 2  = 8 product type/ and

  sealed trait SwitchStatus // or 1 + 1 = 2 sum types
  case object ON extends SwitchStatus
  case object OFF extends SwitchStatus


  //Sum type e.g. Boolean, Option, Either, etc. Or
  sealed trait MyOption[T]
  object MyOption {
    case class MySome[T](t:T) extends MyOption[T]
    case object MyNone extends MyOption[Nothing]
  }
  sealed trait MyEither[L,R]
  object MyEither {
    case class Left[L,R](l:L) extends MyEither[L,R]
    case class Right[L,R](r:R) extends MyEither[L,R]
  }

  /**
    * domain modeling using ADT
    * IoT Domain, representing devices communicating with Http and others with MQtt
    */
  sealed trait IoTProtocol
  object IoTProtocol {
    case class HTTP(host:String, port:String, path:String) extends IoTProtocol
    case class MQtt(host:String, port:String) extends IoTProtocol
  }

  /**
    * One more example, Shipping types
    */
  sealed trait ShippingType
  object ShippingType {
    case object Post extends ShippingType
    case object FedX extends ShippingType
    case object Air extends ShippingType
  }
  /*
  All exercises below are assumed to be solved only using sealed trait , case classes and case objects
  Obviously you can be as creative as you want
   */
  /**
    * Exercise 1
    * Data model of a credit card
    */
  sealed trait CreditCard

  /**
    * Exercise 2
    * Data model of a Paint Application
    */
  sealed trait Paint

  /**
    * lets do some pattern matching
    * https://docs.scala-lang.org/tour/pattern-matching.html
    * https://docs.scala-lang.org/overviews/scala-book/match-expressions.html
    * For more details check here https://scala-lang.org/files/archive/spec/2.12/08-pattern-matching.html
    */

  def chekShippingType(shippingType: ShippingType):String = shippingType match {
    case ShippingType.Post => "It's going to take long..."
    case ShippingType.FedX => "It'll arrive in 4-5 days"
    case ShippingType.Air => "You'll get it tomorrow"
  }

  import IoTProtocol._
  def makeIoTRequest(ioTProtocol: IoTProtocol):String = ioTProtocol match {
    case HTTP(host, port, path) => s"My request is http on $host:$port://$path"
    case MQtt(host, port) => s"My request is MqTT on $host:$port"
  }

  //1.  Here we actually see the usage of unapply with match keyword
  //2.  Wild card or otherwise conditions
  def chekShippingType1(shippingType: ShippingType):String = shippingType match {
    case ShippingType.Post => "It's going to take long..."
    case _ => "It'll arrive sooner"
  }
  //3.  guards in pattern matching (if)
  def makeIoTRequest1(ioTProtocol: IoTProtocol):String = ioTProtocol match {
    case HTTP(host, port, path) if port == "80" => s"My request is http on $host://$path"
    case HTTP(host, port, path)  => s"My request is http on $host:$port//$path"
    case MQtt(host, port) => s"My request is MqTT on $host:$port"
  }
  //4.  Handling alternate cases(|)
  def chekShippingType2(shippingType: ShippingType):String = shippingType match {
    case ShippingType.Post => "It's going to take long..."
    case ShippingType.FedX | ShippingType.Air => "It'll arrive Soon"
  }
  //5.  back ticks and capital letters in pattern match https://users.scala-lang.org/t/upper-case-letters-in-pattern-matching/5947/2
  val str = "X"
  val Yes = "Y"
  val xs = "X"
  str match {
    case Yes => "it's not X"
    case `xs` => "it's an X"
    case _ => "something is wrong"
  }
  //6.  Alias in pattern matching
  def makeIoTRequest3(ioTProtocol: IoTProtocol):String = ioTProtocol match {
    case http@HTTP(host, port, path) => s"My request is http on $http"
    case m@MQtt(host, port) => s"My request is MqTT on $m"
  }
  //7.  sealed traits and match errors in pattern matching


  /**
    * Exercise 4
    * Pattern match on an expression to print what type of value it returns
    */
  def printTypes(printMyType: Any): Unit =
    printMyType match {
      case _ => println("Unknown Type.")
    }

  /**
    * Exercise 5
    * Pattern matching with regular expression
    * Note : uncomment and implement
    */

  val date = raw"""(\d{4})-(\d{2})-(\d{2})""".r

  /*"2004-01-20" match {
    case ??? => "It's a date!"
    case _ => "It's not a valid date"
  }*/

  /*"2004-01-20" match {
    case ??? => ??? //s"$year was a good year for PLs."
  }*/

  /**
    * Exercise 6
    * Pattern match on an Option inside either
    */
  type Input = Either[String, Option[String]]
  /*def getStringFrom(input:Input) : String = input match {
    case ??? => "implement this method"
  }*/

  /**
    * Exercise 7
    * Use Pattern match on tuple for below result
    * {{{
    *   if _1 == _2 then Option(_1 + _2)
    *   if _1 < _2 then Option(_1)
    *   else None
    * }}}
    */
  def computeTuple(t: (Int, Int)): Option[Int] = ???

  /**
    * Exercise 8
    *
    * Pattern match on multiple object containing a domain
    */
  case class Address(
      street: String,
      city: String,
      postalCode: String,
      state: String
  )
  sealed trait PhoneType
  case object HomePhone extends PhoneType
  case object WorkPhone extends PhoneType
  case object Other extends PhoneType

  case class Person(
      firstName: String,
      lastName: String,
      address: Address,
      phoneType: PhoneType
  )
  def checkIfPersonHasWorkPhone(person: Person): Boolean = ???
  def checkIfPersonAreFromGivenCity(person: Person, city: String): Boolean = ???

  sealed trait Errors
  case object InvalidAddress extends Errors
  case object InvalidPerson extends Errors
  //helper functions
  def emptyString: String => Boolean = ???

  //Lets make some smart constructors
  def makeAddress(address: Address): Either[Errors, Address] = ???
  def makePerson(person: Person): Either[Errors, Person] = ???

  //Testing, create a sample person and tryout the person smart constructor

  //How to make scala compiler ignore wrapper classes using AnyVal

  /*
  * Account
  * Checking, Savings, Recurring
  * */

  /*
  * Traffic signal
  * */
}
