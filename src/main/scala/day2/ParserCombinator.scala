package day2

/**
  * We will be implementing a simple mono parser from here http://www.cs.nott.ac.uk/~pszgmh/monparsing.pdf
  * To begin with the exercise lets create a type for the parser
  *
  * Note:
  *  this is the version one of the parser lib, we'll come back to implement better error handling in future
  * */
object ParserCombinator {
  /*
    val v  = "1.2.3"
    Semver(Major(1), Minor(2), Patch(3))

  * major: String => (Int,String) = str => (str.head, str.tail)
  * minor: String => (Int, String) = ???
  * patch: String => (Int, String) = ???
  *
  * val (m, r1) = major(v)
  * val (mi, r2) = minor(r1)
  * val (p, r3) = patch(r2)
  *
    Semver(Major(m), Minor(mi), Patch(p))

    interface Parser<A> {
      def parse(input:String):(A,String)
    }
    class Parser<A> {
      private Functional<String, Tuple<A,String>> parser;
      public Parser(Functional<String, Tuple<A,String>> p){
        this.parser = p
      }
    }
    class Parser[A](parser:String => (A, String)){
      val p:String => (A, String) = parser
    }

    case class Parser[A](p:String => (A, String)){
      def map
      def flatMap
    }

    for {
      ma <- major
      mi <- minor
      pa <- patch
    } yield Semver(Major(ma), Minor(mi), Patch(pa))

  * */

  //extending AnyVal removes the wrapper overhead at runtime for more information check here https://docs.scala-lang.org/overviews/core/value-classes.html
  case class Parser[A](parse: String => List[(A, String)]) extends AnyVal

  object Parser {
    //Now that we have a parser type, let's create some primitive types
    //pure: this parser will always give a success
    def result[A]: A => Parser[A] = a => Parser(in => List((a, in)))

    //Zero parser is opposite of result, this one always fails
    def zero[A]: Parser[A] = Parser(_ => List())

    //now we will make some productive parsers
    //1. item parser, on success it take the first char and on failure it returns an empty list
    def item1: Parser[Char] = Parser(in => {
      in.headOption match {
        case Some(v) => List((v, in.tail))
        case _ => List()
      }
    })

    def item: Parser[Char] =
      Parser(in => in.headOption.fold(List.empty[(Char,String)])(v => List((v, in.tail))))

    //introducing parser combinators
    //2 seq, is like a composition of two parsers, think more of a product
    def seq1[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] =
      pa => pb => Parser(in => {
        pa.parse(in).headOption.fold(List.empty[((A,B),String)])(result1 => {
          pb.parse(result1._2).headOption.fold(List.empty[((A,B),String)]){
           result2 =>  List(((result1._1, result2._1),result2._2))
          }
        })
      })

    //this one ia going to be the most important parser combinator
    //3. Bind
    def bind[A, B]: Parser[A] => (A => Parser[B]) => Parser[B] = ???

    //Exercise: try to implement seq using bind
    def seq2[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] = ???

    //with flatmap
    def seq3[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] = ???

    //with for expression
    def seq[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] = ???

    val versionParser1: Parser[Version.SemVer] = ???

    //Exercise sat combinator; Hint: implement using bind and item
    def sat: (Char => Boolean) => Parser[Char] = ???

    //implement car parser using sat
    def char: Char => Parser[Char] = ???

    //implement digit parser using sat
    def digit: Parser[Char] = ???

    //implement lower case parser using sat
    def lower: Parser[Char] = ???

    //implement upper case parser using sat
    def upper: Parser[Char] = ???

    val versionParser2: Parser[Version.SemVer] = ???

    //implement a parser to parse a sequence of chars .i.e a word
    def word: Parser[String] = ???

    //you might also need a parser for integer, if you make word a generic one you can reuse

    //that's it now you can start implementing the below functions.
    //Note: If you feel there can be more combinators, operators derived which can help to implement please go ahead and implement those

    /**
      * Parse the string for a version
      *  1.2.3
      * */
    sealed trait Version
    object Version {
      case class Major(int: Int) extends Version
      case class Minor(int: Int) extends Version
      case class Patch(int: Int) extends Version

      case class SemVer(major: Major, minor: Minor, patch: Patch)
    }

    /**
      * Exercise 1
      * Parse string to get a person
      * String is a ini file format data
      * */
    val stringToParse: String =
      """
        |[Person]
        |name=John
        |age=35
        |""".stripMargin

    case class Person(name: String, age: Int)

    /**
      * Exercise 2
      * enhance version parsing with the semver specification
      * https://semver.org/
      * Idea is to have release and metadata in the version
      * */

  }
}
