package day2

object ParametricPolymorphism {

  //Introducing variance, covariance, contravariance and invariant

  /*
    Exercise 1
   * Why the Function{N} trait in scala have contravariant parameters
   * */

  //Higher kinded types, how to create one.
  //Kind *
  trait F0
  //kind * -> *
  trait F1[T]
  //kind * -> * -> *
  trait F2[T[_]]
  //kind * -> * -> * -> *
  trait F3[T[_,_]]

  /*
    Exercise 2
   * Creating a List Data structure
   * */

  //GADT

  //more to add here exercise
}
