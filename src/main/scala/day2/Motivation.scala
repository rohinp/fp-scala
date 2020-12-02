package day2


object Motivation {
  //The question is take from red book (Functional Programming in Scala), one of the most famous book to learn FP in scala
  /**
   * <p> To gain experience, lets implement a finite state automaton that models a simple candy dispenser.
   * <p> The machine has two types of input: you can insert a coin, or you can turn the knob to dispense candy.
   * <p> It can be in one of two states: locked or unlocked.
   * <p> It also tracks how many candies are left and how many coins it contains.
   *  <br>
   * <p>The rules of the machine are as follows:
   * <ul>
   * <li> Inserting a coin into a locked machine will cause it to unlock if there’s any candy left.
   * <li> Turning the knob on an unlocked machine will cause it to dispense candy and become locked.
   * <li> Turning the knob on a locked machine or inserting a coin into an unlocked machine does nothing.
   * <li> A machine that’s out of candy ignores all inputs.
  * */
  sealed trait Input
  case object Coin extends Input
  case object Turn extends Input

  case class Machine(locked: Boolean, candies: Int, coins: Int)

  /**
   * <p>The method simulateMachine should operate the machine based on the list of inputs
   * and return the number of coins and candies left in the machine at the end. For example,
   * if the input Machine has 10 coins and 5 candies, and a total of 4 candies are successfully
   * bought, the output should be (14, 1).
   * <p> this one is like our main function
   * {{{
   *  def simulateMachine(inputs: List[Input]): ???
   * }}}
   *
   * */

}
