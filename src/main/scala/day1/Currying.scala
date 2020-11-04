package day1

import java.util.Date

import cats.data._
import cats.implicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

sealed trait Color
object Color {
  case object Green extends Color
  case object Red extends Color
  case object Blue extends Color
  case object Yellow extends Color
  case object Black extends Color
}

sealed trait Shape {
  case class Circle(radius: Int)
  case class Rectangle(length: Int, breadth: Int)
  case class RightTriangle(base: Int, height: Int)
}

case class ColoredShape(shape: Shape, color: Color)

object Currying {
  def colorShape: Color => Shape => ColoredShape = ???
  def colorShape2(color: Color, shape: Shape): ColoredShape = ???

  def colorShapeGreen = colorShape(Color.Red)
  // this is not possible
  // def colorShapeGreen2 = colorShape2(Color.Red)

  //though you can force the compiler to convert it to a function and then use it
  //using the underscore
  //but obviously having it curried at first go is always a better option
  def colorShapeGreen2 = colorShape2(Color.Red, _)


  //Also it helps to identify the abstractions more easily
  //Lets take the below example taken from https://github.com/debasishg/frdomain
  sealed trait AccountType
  case object Checking extends AccountType
  case object Savings extends AccountType

  //dummy repo
  trait AccountRepository

  //version 1
  trait AccountService1[Account, Amount, Balance] {
    def open(no: String,
             name: String,
             openingDate: Option[Date],
             accountType: AccountType,
             repository: AccountRepository): Future[Account]

    def close(no: String,
              closeDate: Option[Date],
              repository: AccountRepository): Future[Account]

    def debit(no: String,
              amount: Amount,
              repository: AccountRepository): Future[Account]

    def credit(no: String,
               amount: Amount,
               repository: AccountRepository): Future[Account]

    def balance(no: String,
                repository: AccountRepository): Future[Balance]

    def transfer(from: String,
                 to: String,
                 amount: Amount,
                 repository: AccountRepository): Future[(Account,Account)]
  }

  //version 2, identify the repetition pattern in parameter and lets curry them
  trait AccountService2[Account, Amount, Balance] {
    def open(no: String,
             name: String,
             openingDate: Option[Date],
             accountType: AccountType): AccountRepository => Future[Account]

    def close(no: String,
              closeDate: Option[Date]): AccountRepository => Future[Account]

    def debit(no: String,
              amount: Amount): AccountRepository => Future[Account]

    def credit(no: String,
               amount: Amount): AccountRepository => Future[Account]

    def balance(no: String): AccountRepository => Future[Balance]

    def transfer(from: String,
                 to: String,
                 amount: Amount): AccountRepository => Future[(Account,Account)]
  }

  //version 3, that sure looks an external resource needs to be injected
  // lets as an intermediate step give a type alias
  trait AccountService3[Account, Amount, Balance] {
    type AccountOperation[A] = AccountRepository => Future[A]
    def open(no: String,
             name: String,
             openingDate: Option[Date],
             accountType: AccountType): AccountOperation[Account]

    def close(no: String,
              closeDate: Option[Date]): AccountOperation[Account]

    def debit(no: String,
              amount: Amount): AccountOperation[Account]

    def credit(no: String,
               amount: Amount): AccountOperation[Account]

    def balance(no: String): AccountOperation[Balance]

    def transfer(from: String,
                 to: String,
                 amount: Amount): AccountOperation[(Account,Account)]
  }

  //version 4, once we have a alias
  //We still cant do much, let's give it some power and see the difference
  trait AccountService4[Account, Amount, Balance] {
    type AccountOperation[A] = AccountRepository => Future[A]
    def open(no: String,
             name: String,
             openingDate: Option[Date],
             accountType: AccountType): AccountOperation[Account]

    def close(no: String,
              closeDate: Option[Date]): AccountOperation[Account]

    def debit(no: String,
              amount: Amount): AccountOperation[Account]

    def credit(no: String,
               amount: Amount): AccountOperation[Account]

    def balance(no: String): AccountOperation[Balance]

    def transfer(from: String,
                 to: String,
                 amount: Amount): AccountOperation[(Account,Account)]
  }

  //version 5
  trait AccountService5[Account, Amount, Balance] {
    type AccountOperation[A] = Kleisli[Future,AccountRepository,A]
    def open(no: String,
             name: String,
             openingDate: Option[Date],
             accountType: AccountType): AccountOperation[Account]

    def close(no: String,
              closeDate: Option[Date]): AccountOperation[Account]

    def debit(no: String,
              amount: Amount): AccountOperation[Account]

    def credit(no: String,
               amount: Amount): AccountOperation[Account]

    def balance(no: String): AccountOperation[Balance]

    def transfer(from: String,
                 to: String,
                 amount: Amount): AccountOperation[(Account,Account)] = for {
      a <- debit(from, amount)
      b <- credit(to, amount)
    } yield (a, b)
  }

}
