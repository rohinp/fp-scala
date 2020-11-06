package day1

object ParserCombinator {
  //We will be implementing a simple mono parser from here http://www.cs.nott.ac.uk/~pszgmh/monparsing.pdf
  //To begin with the exercise lets create a type for the parser
  //extending AnyVal removes the wrapper overhead at runtime for more information check here https://docs.scala-lang.org/overviews/core/value-classes.html
  case class Parser[A](parse:String => List[(A,String)]) extends AnyVal {
    import Parser._
    def flatMap[B](f:A => Parser[B]):Parser[B] = bind(this)(f)
    def map[B](f:A => B):Parser[B] = fmap(this)(f)
    def -->[B] (pb:Parser[B]): Parser[(A, B)] = seq(this)(pb)
    def <*[B](pb:Parser[B]):Parser[A] = productL(this)(pb)
    def *>[B](pb:Parser[B]):Parser[B] = productR(this)(pb)
  }

  object Parser {
    //Now that we have a parser type, let's create some primitive types
    //this parser will always give a success
    def result[A]: A => Parser[A] = a => Parser(input => List((a, input)))

    //Zero parser is opposite of result, this one always fails
    def zero[A]: Parser[A] = Parser(_ => List())

    //now we will make some productive parsers
    //1. item parser, on success it take the first char and on failure it returns an empty list
    def item: Parser[Char] = Parser(input => {
      val list = input.toList
      list.headOption.fold(List.empty[(Char,String)])(c => List((c, list.tail.mkString)))
    })

    //introducing parser combinators
    //2 seq, is like a composition of two parsers, think more of a product
    def seq[A, B]: Parser[A] => Parser[B] => Parser[(A, B)] = p1 => p2 => Parser{
      input => {
        p1.parse(input).headOption.fold(List.empty[((A,B),String)]){
            r1 => p2.parse(r1._2).headOption.fold(List.empty[((A,B),String)])(r2 => List(((r1._1, r2._1), r2._2)))
          }
      }
    }

    //this one ia going to be the most important parser combinator
    //3. Bind
    def bind[A,B]:Parser[A] => (A => Parser[B]) => Parser[B] = p => f => Parser {
      input => {
        p.parse(input).headOption
          .fold(List.empty[(B,String)]){
            ta => f(ta._1).parse(ta._2)
          }
      }
    }

    def fmap[A,B]:Parser[A] => (A => B) => Parser[B] = p => f => bind(p)(a => result(f(a)))

    //product Left
    def productL[A,B]:Parser[A] => Parser[B] => Parser[A] = pa => pb => for {
      a <- pa
      _ <- pb
    } yield a

    //product Right
    def productR[A,B]:Parser[A] => Parser[B] => Parser[B] = pa => pb => for {
      _ <- pa
      b <- pb
    } yield b

    //sequence
    /*def sequence[A](lp:Parser[A]*): Parser[List[A]] = Parser{ input =>
      def loop(remainingInput:String,l:List[Parser[A]], acc:List[A]): List[(List[A], String)] = (remainingInput, l) match {
        case ("", _) => List.empty[(List[A],String)]
        case (in, Nil) => List((acc,in))
        case (in, p::ps) =>
          p.parse(in).headOption
            .fold(List.empty[(List[A],String)]){
              r => loop(r._2, ps, acc.appended(r._1))
            }
      }
      loop(input, lp.toList, List.empty[A])
    }*/
    //Exercise: try to implement seq using bind

    //Exercise sat combinator; Hint: implement using bind and item
    def sat:(Char => Boolean) => Parser[Char] = f => bind(item)(c => if(f(c)) result(c) else zero)

    //implement car parser using sat
    def char:Char => Parser[Char] = c => sat(_ == c)

    //implement digit parser using sat
    def digit:Parser[Char] = sat(_.isDigit)

    //implement lower case parser using sat
    def lower:Parser[Char] = sat(_.isLower)

    //implement upper case parser using sat
    def upper:Parser[Char] = sat(_.isUpper)

    //lets build a choice combinator
    def plus[A]: Parser[A] => Parser[A] => Parser[A] = p1 => p2 => Parser(input => p1.parse(input).headOption.fold(p2.parse(input))(List(_)))

    def letter: Parser[Char] = plus(lower)(upper)
    def alphanum: Parser[Char] = plus(letter)(digit)

    def word:Parser[Char] => Parser[String] = p => {
      def neWord: Parser[String] = bind(p){
        l => bind(word(p))(xs => result(xs.prepended(l)))
      }
      plus(neWord)(result(""))
    }

    def string:Parser[String] = word(letter)
    def integer:Parser[String] = word(digit)

    /**
     * Parse the string for a version
     *  1.2.3
     * */
    case class Major(int:Int)
    case class Minor(int:Int)
    case class Patch(int:Int)
    case class Version(major: Major, minor: Minor, patch: Patch)
    def version:Parser[Version] = bind(integer){
      major => bind(char('.')){
        _ => bind(integer){
          minor => bind(char('.')){
            _ => fmap(integer){
              patch => Version(Major(major.toInt), Minor(minor.toInt), Patch(patch.toInt))
            }
          }
        }
      }
    }

    def version1:Parser[Version] = for {
      major <- integer <* char('.')
      minor <- integer <* char('.')
      patch <- integer
    } yield Version(Major(major.toInt),Minor(minor.toInt),Patch(patch.toInt))

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
        |""".stripMargin.trim

    case class Person(name:String, age:Int)

    def person:Parser[Person] = bind(char('[')){
      _ => bind(string){
        _ => bind(char(']')){
          _ => bind(char('\n')){
            _ => bind(string){
              _ => bind(char('=')){
                _ => bind(string){
                  name => bind(char('\n')){
                    _ => bind(string){
                      _ => bind(char('=')){
                        _ => fmap(integer){
                          age => Person(name,age.toInt)
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    def person1:Parser[Person] = for {
      name <- (char('[') --> string -->  char(']') --> char('\n') --> string --> char('=')) *> string
      age <- (char('\n') --> string --> char('=')) *> integer
    } yield Person(name,age.toInt)

    /**
    * Exercise 2
     * enhance version parsing with the semver specification
     * https://semver.org/
     * Idea is to have release and metadata in the version
    * */

  }
}
