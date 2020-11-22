package day2

object TypeLevel {
  //phantom types

  /*
  * Subtyping and generics interactions can introduce
  * 1. type bounds or constraint on a generic type
  * 2. variance, all about relationship between types
  * */

  //lets tal about bounds
  //upper bound
  //lower bound
  //mixed bound

  //Introducing variance, covariance, contravariance and invariant
  trait Fruit {
    val colour:String
  }
  case class Apple(colour: String) extends Fruit
  case class Orange(colour: String) extends Fruit

  //lets not create a box
  case class Box[A](a:A)


  /*
    Exercise 1
   * Why the Function{N} trait in scala have contravariant parameters
   * */

  //Higher kinded types, how to create one.
  trait F0 //Kind *
  trait F1[T] //kind * -> *
  trait F2[T[_]] //kind * -> * -> *
  trait F3[F[_[_]]] // kind * -> * -> (* -> *)
  trait F3M[T[_,_]] //kind * -> * -> [* , *]

  //GADT

  //more to add here exercise

  //Existential types
  ////(https://blog.knoldus.com/back2basics-existential-types-in-scala/)

  //basic type lambda
}
