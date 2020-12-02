package day2

/**
  * We will be implementing a simple mono parser from here http://www.cs.nott.ac.uk/~pszgmh/monparsing.pdf
  * To begin with the exercise lets create a type for the parser
  *
  * Note:
  *  this is the version one of the parser lib, we'll come back to implement better error handling in future
  * */
object ParserCombinator {
  //extending AnyVal removes the wrapper overhead at runtime for more information check here https://docs.scala-lang.org/overviews/core/value-classes.html
  case class Parser[A](parse: String => List[(A, String)]) extends AnyVal {
    import Parser._
    def map[B](f: A => B): Parser[B] = bind(this)(a => result(f(a)))
    def flatMap[B](f: A => Parser[B]): Parser[B] = bind(this)(f)
  }

  object Parser {
    //Now that we have a parser type, let's create some primitive types
    //this parser will always give a success
    def result[A]: A => Parser[A] = a => Parser(in => List((a, in)))

    //Zero parser is opposite of result, this one always fails
    def zero[A]: Parser[A] = Parser(_ => List())

    //now we will make some productive parsers
    //1. item parser, on success it take the first char and on failure it returns an empty list
    def item: Parser[Char] =
      Parser(in =>
        in.headOption.fold(List.empty[(Char, String)])(ch =>
          List((ch, in.tail))
        )
      )

    //introducing parser combinators
    //2 seq, is like a composition of two parsers, think more of a product
    def seq1[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] =
      pa =>
        pb =>
          Parser[(A, B)](in => {
            val result1 = pa.parse(in)
            result1.headOption.fold(List.empty[((A, B), String)]) { tas =>
              {
                val result2 = pb.parse(tas._2)
                result2.headOption.fold(List.empty[((A, B), String)]) { tbs =>
                  List(((tas._1, tbs._1), tbs._2))
                }
              }
            }
          })

    //this one ia going to be the most important parser combinator
    //3. Bind
    def bind[A, B]: Parser[A] => (A => Parser[B]) => Parser[B] =
      pa =>
        f =>
          Parser[B](in => {
            pa.parse(in).headOption.fold(List.empty[(B, String)]) { t =>
              f(t._1).parse(t._2)
            }
          })

    //Exercise: try to implement seq using bind
    def seq2[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] =
      pa => pb => bind(pa)(a => bind(pb)(b => result((a, b))))

    def seq3[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] =
      pa => pb => pa.flatMap(a => pb.map(b => (a,b)))

    def seq[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] =
      pa =>
        pb =>
          for {
            a <- pa
            b <- pb
          } yield (a, b)

    val versionParser1: Parser[(Major, Minor, Patch)] = for {
      tuple_major <- seq(item)(item)
      tuple_minor <- seq(item)(item)
      patch <- item
    }yield (Major(tuple_major._1.toString.toInt), Minor(tuple_minor._1.toString.toInt), Patch(patch.toString.toInt))

    //Exercise sat combinator; Hint: implement using bind and item
    def sat: (Char => Boolean) => Parser[Char] = f =>
      item.flatMap(c => if(f(c)) result(c) else zero)

    //implement car parser using sat
    def char: Char => Parser[Char] = ch => sat(_ == ch)

    //implement digit parser using sat
    def digit: Parser[Char] = sat(_.isDigit)

    //implement lower case parser using sat
    def lower: Parser[Char] = sat(_.isLower)

    //implement upper case parser using sat
    def upper: Parser[Char] = sat(_.isUpper)

    val versionParser2: Parser[(Major, Minor, Patch)] = for {
      tuple_major <- seq(digit)(char('.'))
      tuple_minor <- seq(digit)(char('.'))
      patch <- digit
    } yield (Major(tuple_major._1.toString.toInt), Minor(tuple_minor._1.toString.toInt), Patch(patch.toString.toInt))

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
    case class Major(int: Int) extends Version
    case class Minor(int: Int) extends Version
    case class Patch(int: Int) extends Version

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
