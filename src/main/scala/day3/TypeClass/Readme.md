#Polymorphism

### Parametric Polymorphism
a function or a data type can be written generically so that it can handle values *identically* without 
depending on their type [Example](../TypeClass/parametricpolymorphism/MyGenericComparator.scala)

### Subtyping Polymorphism
* [Interface](../TypeClass/subtypingpolymorphism/MyComparable.scala)
* [Implementation](../TypeClass/subtypingpolymorphism/Company.scala)

### ad hoc Polymorphism
a function is defined over several types, acting differently for each type [Example](../TypeClass/adhocpolymorphism/Sorting3.scala)

#### Type Class Pattern in Scala
1. The Type Class : A type class is an interface or API that represents some functionality that we want to implement.
   [Example](../TypeClass/adhocpolymorphism/MyComparator.scala)
   
2. Type Class Instances : The instances of a type class provide implementations of the type class for the types we care about.
   [Example - Companion object of MyComparator](../TypeClass/adhocpolymorphism/MyComparator.scala)
   
3. Type class interfaces
   A type class interface is any functionality we expose to users. Interfaces are generic methods that accept 
   instances of the type class as implicit parameters.
   
   There are two common ways of specifying an interface:
   1. Interface Objects : simplest way of creating an interface is to place methods in a singleton object. 
      [Examples]
      * [1](../TypeClass/adhocpolymorphism/Sorting6.scala)
      * [2](../TypeClass/adhocpolymorphism/Sorting7.scala)
      * [3](../TypeClass/adhocpolymorphism/Sorting8.scala)
   2. Interface Syntax :  use extension methods to extend existing types with interface methods.
    [Example](../TypeClass/adhocpolymorphism/Sorting9.scala)
