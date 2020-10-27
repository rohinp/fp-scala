package day1

object ParserCombinator {
  //We will be implementing a simple mono parser from here http://www.cs.nott.ac.uk/~pszgmh/monparsing.pdf
  //To begin with the exercise lets create a type for the parser
  //extending AnyVal removes the wrapper overhead at runtime for more information check here https://docs.scala-lang.org/overviews/core/value-classes.html
  case class Parser[A](parse:String => List[(A,String)]) extends AnyVal

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

  }
}
