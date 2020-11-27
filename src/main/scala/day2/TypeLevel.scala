package day2

object TypeLevel {
  //From this paper https://adriaanm.github.io/files/higher.pdf

  /**
   * <p>An abstract type member is similar to a type parameter. The main difference between parameters and members is their scope and visibility.
   * <p>A type parameter is syntactically part of the type that it parameterizes, whereas a type member – like value members – is encapsulated,
   * <p>and must be selected explicitly. Similarly, type members are inherited, while type parameters are local to their class
   */
  trait MyType[T] //type parameter
  trait MyType_{ // abstract class with abstract type member
    type T
  }

  class M extends MyType[Int]

  // because of type erasure, the type information is lost after compilation and thus the code works
  // and doesn't complains for overriding the abstract type as it would have complained in case of a concrete type
  class M1 extends MyType_

  //Also remember that this wont work either, incompatible types
  /**
   * {{{
   *   trait MyType_{ // abstract class with abstract type member and an abstract val
   *     type T
   *     val x:T
   *   }
   *  class M22[T](val x:T) extends MyType_
   * }}}
   * */
  class M2 extends MyType_ { type T = Int } //concrete implementation with type T as Int

  //you can access the encapsulated type using project operator
  val t:M2#T = 100

  /**
   * <p>So both type parameter and abstract types are useful
   * <p>more details are there in the paper for those who are interested
   * <br>
   * <p> Next up type constructors, as the name suggest a type constructor is basically a type takes another type
   * <p> for example
   * <table width="600" >
   *   <tr>
   *   <td>Kinds</td>
   *   <td>*</td>
   *   <td>* -> *</td>
   *   <td>* -> * -> *</td>
   *   </tr>
   *   <tr>
   *   <td>Types</td>
   *   <td>
   *     <br>
   *     <p> proper types
   *     <p> Any
   *     <p> Int, List[Int], Pair[Int,Int]
   *   </td>
   *   <td>List</td>
   *   <td>Pair</td>
   *   </tr>
   *   <tr>
   *   <td>Values</td>
   *   <td>
   *     <br>
   *     1, List(1,2,3), (1,2)
   *   </td>
   *   <td></td>
   *   <td></td>
   *   </tr>
   * </table>
   *
   * */



  //Reducing Code Duplication with Type Constructor Polymorphism
  object RedundantCodeDueToType {

    /**
     * Scala implementation of the trait
     * Iterable[T]. It contains an abstract method filter and a
     * convenience method remove. Subclasses should implement
     * filter so that it creates a new collection by retaining only
     * the elements of the current collection that satisfy the predicate p. This predicate is modelled as a function that takes
     * an element of the collection, which has type T, and returns
     * a Boolean. As remove simply inverts the meaning of the
     * predicate, it is implemented in terms of filter.
     * */
    trait Iterable[T] {
      def filter(p: T => Boolean): Iterable[T]
      def remove(p: T => Boolean): Iterable[T] = filter(x => !p(x))
    }

    /**
     * For consistency, remove
     * should have the same result type, but the only way to achieve
     * this is by overriding it as well. The resulting code duplication is a clear indicator of a limitation of the type system:
     * both methods in List are redundant, but the type system is
     * not powerful enough to express them at the required level of
     * abstraction in Iterable.
     * */
    trait List[T] extends Iterable[T] {
      override def filter(p: T => Boolean): List[T]
      override def remove(p: T => Boolean): List[T] = filter(x => !p(x))
    }

  }
  /**
   * The improved Iterable now
   * takes two type parameters: the first one, T, stands for the
   * type of its elements, and the second one, Container, represents the type constructor that determines part of the result
   * type of the filter and remove methods. More specifically,
   * Container is a type parameter that itself takes one type parameter
   * */
  object RemovingRedundancy {
    trait Iterable[T, Container[T]] {
      def filter(p: T => Boolean): Container[T]
      def remove(p: T => Boolean): Container[T] = filter(x => !p(x))
    }

    trait List[T] extends Iterable[T, List]
  }


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
