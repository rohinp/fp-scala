package day2

object TypeLevel {
  //From this paper https://adriaanm.github.io/files/higher.pdf

  /**
    * <p>An abstract type member is similar to a type parameter. The main difference between parameters and members is their scope and visibility.
    * <p>A type parameter is syntactically part of the type that it parameterizes, whereas a type member – like value members – is encapsulated,
    * <p>and must be selected explicitly. Similarly, type members are inherited, while type parameters are local to their class
    */
  trait MyType[T] //type parameter
  trait MyType_ { // abstract class with abstract type member
    type T
  }

  class M extends MyType[Int]
  //class M_ extends MyType

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
  class M2 extends MyType_ {
    type T = Int
  } //concrete implementation with type T as Int

  //you can access the encapsulated type using project operator
  val t: M2#T = 100

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
  val aValue: List[Int] = List(1, 2, 3) //*
  type AType[A] = List[A] // * -> *
  type AType_2[A, B] = (A, B) // * -> * -> *

  //Type bounds
  object typeBounds {
    object upperBounds {

      /**
        * <p> An upper type bound
        * {{{T <: A}}}
        * declares that type variable T refers to a subtype of type A
        * <p>As shown in the below example, with the help of type bound constraint
        * we cna access the colour function on generic type
        * */
      sealed trait Fruit {
        def colour: String
      }
      case class Apple(colour: String) extends Fruit
      case class Orange(colour: String) extends Fruit

      case class BoxFruit[F <: Fruit](f: F) {
        def boxColour: String = f.colour
      }
      val appleBox: BoxFruit[Apple] = BoxFruit(Apple("green"))
      val orangeBox: BoxFruit[Orange] = BoxFruit(Orange("orange"))

      /**
        * This is from source code doc of scala.typeConstraints.scala file
        * <p>An instance of `A <:< B` witnesses that `A` is a subtype of `B`.
        * Requiring an implicit argument of the type `A <:< B` encodes
        * the generalized constraint `A <: B`.
        *
        * well for example, rewrite the BoxFruit container
        * */
      case class Box[T](t: T) {

        /**
          * There are two issues with the below code
          * 1. T is shadowed
          * 2. in t.colour colour is not a member of type T
          */
        //def messageForFruit[T <: Fruit]:String = s"The colour of $t is ${t.colour}"
        //In this case the <:< operator is for resque although it just encode the behaviour of <:
        def messageForFruit(implicit ev: T <:< Fruit): String =
          s"The colour of $t is ${t.colour}"
        //basically the above code translates to something like this
        //it searches for an instance of the function T => Fruit
        def messageForFruit1(implicit ev: T => Fruit): String =
          s"The colour of $t is ${t.colour}"
      }

      //Again we saw the use of <:< operator in flatten
      def flatten[A, B](l: List[B])(implicit ev: B <:< List[A]): List[A] =
        l.flatten
      //which could have also be written in terms of <:, after all <:< is just an encoding of <:
      def flatten1[A, B <: List[A]](l: List[B]): List[A] = l.flatten
      //the only concern is how you call the method, because if you don't specify the type
      //for compiler those are Nothing, so this works
      //flatten1(List(1,2,3)) // inferred type is [Nothing, Int], also doesn't works
      //And this doesn't work
      //flatten1[Int,List[Int]](List(1,2,3))
      flatten1[Int, List[Int]](List(List(1), List(2))) //this works

      /*
      In short there are three reasons you can use <:< witness instead of <: upper bound
      1. If your type is getting shadowed
      2. to help scala compiler with type inference
      3. If instead of upper bound you want to prove a type relation at compile time
       */
    }
    object lowerBounds {

      /**
        * Inorder to understand lower bounds first we need to also understand type variance
        * as the example given below concerns with contravariant relation
        * */
      trait Node[+B] {
        def prepend[U >: B](elem: U): Node[U]
      }

      case class ListNode[+B](h: B, t: Node[B]) extends Node[B] {
        def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)
        def head: B = h
        def tail: Node[B] = t
      }

      case class Nil[+B]() extends Node[B] {
        def prepend[U >: B](elem: U): ListNode[U] = ListNode(elem, this)
      }
    }
    object mixBounds {
      //I'll keep this on you to research and understand
      //hint: It's pretty simple
    }

  }

  object variance {
    //Introducing variance, covariance, contravariance and invariant
    //example taken form here https://downloads.lightbend.com/website/scaladays/2018/ny/slides/type-parameter-power-up-variance-bounds-and-inference.pdf
    trait Animal {
      def name: String
    }
    trait Pet extends Animal
    case class Cat(name: String) extends Pet
    case class Dog(name: String) extends Pet

    object in_variance {
      class InvariantWrapper[A](wrapped: A) {
        def unwrapped: A = wrapped
      }
      val w: InvariantWrapper[Cat] = new InvariantWrapper(Cat("Morris"))

      class SubWrapper[A](wrapped: A) extends InvariantWrapper(wrapped) {}

      val sw: InvariantWrapper[Cat] = new SubWrapper(Cat("Milo"))

      //doesn't work
      //val aw: InvariantWrapper[Animal] = new InvariantWrapper[Cat](Cat("Bill"))
    }
    object co_variance{
      class CovariantWrapper[+A](wrapped: A) {
        def unwrapped: A = wrapped
      }
      def doIt(thing: CovariantWrapper[Animal]):Unit = ???

      doIt(new CovariantWrapper[Cat](Cat("Garfield")))
      doIt(new CovariantWrapper[Dog](Dog("Odie")))
      //doesn't work
      //doIt(new CovariantWrapper[Any](Cat("Nermal")))

      //good for containers and producers
    }
    object contra_variance{
      trait Keeper[-A] {
        def tend(input: A): Unit
      }
      class DogSitter extends Keeper[Dog] {
        override def tend(input: Dog): Unit = ???
      }
      class ZooKeeper extends Keeper[Animal] {
        override def tend(input: Animal): Unit = ???
      }
      class PetSitter extends Keeper[Pet] {
        override def tend(input: Pet): Unit = ???
      }
      val ds: Keeper[Dog] = new DogSitter
      ds.tend(Dog("Tintin"))
      val zoo: Keeper[Dog] = new ZooKeeper
      zoo.tend(Dog("Scooby"))
      //this doesn't work
      /*
      val petco: Keeper[Pet] = new DogSitter
      petco.tend(Dog("Ceasar"))
      */

      //contravariance are good for  processors or consumers
      val petTrainer:Pet => String = _.name + " is with the trainer"
      val animalOwner:Animal => String = _.name + " is with the Owner"
      val dogTrainer:Dog => String = _.name + " dog is in training"

      def petStatus(pet: Pet)(f:Pet => String):String = f(pet)
      petStatus(Dog("Fluffy"))(petTrainer)
      petStatus(Dog("Fluffy"))(animalOwner)
      //doesn't work
      //petStatus(Dog("Fluffy"))(dogTrainer)
      //time to go back to lower bound example
    }

  }

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
    trait Iterable[T, Container[_]] {
      def filter(p: T => Boolean): Container[T]
      def remove(p: T => Boolean): Container[T] = filter(x => !p(x))
    }

    trait List[T] extends Iterable[T, List]
  }

  //Existential types

  //basic type lambda
}
