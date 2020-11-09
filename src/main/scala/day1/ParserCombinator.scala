package day1

/**
 * We will be implementing a simple mono parser from here http://www.cs.nott.ac.uk/~pszgmh/monparsing.pdf
 * To begin with the exercise lets create a type for the parser
 *
 * Note:
 *  this is the version one of the parser lib, we'll come back to implement better error handling in future
 * */
object ParserCombinator {
  //extending AnyVal removes the wrapper overhead at runtime for more information check here https://docs.scala-lang.org/overviews/core/value-classes.html
  case class Parser[A](parse:String => List[(A,String)]) extends AnyVal {
    //1. implement flatMap and map one below exercises are done
    //2. implement left and right product and see how it helps
  }

  object Parser {
    //Now that we have a parser type, let's create some primitive types
    //this parser will always give a success
    def result[A]: A => Parser[A] = ???

    //Zero parser is opposite of result, this one always fails
    def zero[A]: Parser[A] = ???

    //now we will make some productive parsers
    //1. item parser, on success it take the first char and on failure it returns an empty list
    def item: Parser[Char] = ???

    //introducing parser combinators
    //2 seq, is like a composition of two parsers, think more of a product
    def seq[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] = ???

    //this one ia going to be the most important parser combinator
    //3. Bind
    def bind[A,B]:Parser[A] => (A => Parser[B]) => Parser[B] = ???

    //Exercise: try to implement seq using bind

    //Exercise sat combinator; Hint: implement using bind and item
    def sat:(Char => Boolean) => Parser[Char] = ???

    //implement car parser using sat
    def char:Char => Parser[Char] = ???

    //implement digit parser using sat
    def digit:Parser[Char] = ???

    //implement lower case parser using sat
    def lower:Parser[Char] = ???

    //implement upper case parser using sat
    def upper:Parser[Char] = ???

    //implement a parser to parse a sequence of chars .i.e a word
    def word:Parser[String] = ???

    //you might also need a parser for integer, if you make word a generic one you can reuse

    //that's it now you can start implementing the below functions.
    //Note: If you feel there can be more combinators, operators derived which can help to implement please go ahead and implement those

    /**
     * Parse the string for a version
     *  1.2.3
     * */
    sealed trait Version
    case class Major(int:Int) extends Version
    case class Minor(int:Int) extends Version
    case class Patch(int:Int) extends Version


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

    case class Person(name:String, age:Int)

    /**
    * Exercise 2
     * enhance version parsing with the semver specification
     * https://semver.org/
     * Idea is to have release and metadata in the version
    * */

  }
}
