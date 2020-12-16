package day3.TypeClass

//Exercise
// 1. Define a typeclass Printable[A] containing a single method format.
//    format should accept a value of type A and return a String.
// 2. Create an object PrintableInstances containing instances of Printable for String and Int.
// 3. Define an object Printable with a generic interface method:
//    format accepts a value of type A and a Printable of the corresponding type.
//    As this is the interface method, the Printable parameter should be implicit.
//    It uses the relevant Printable to convert the A to a String.
// 4. Create an object called PrintableSyntax.
// 5. Inside PrintableSyntax define an implicit class Print- ableOps[A] to wrap up a value of type A.
// 6. In Printable Ops define the following method:
//    format accepts an implicit Printable[A] and returns a String representation of the wrapped A;
// 7. Use extension methods to print Employee