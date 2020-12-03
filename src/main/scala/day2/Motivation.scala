package day2

import scala.annotation.tailrec

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

  object v1 {
    //probably it's a pretty easy problem to update a machine based on single input
    def dispenseMachine: Input => Machine => Machine =
      input =>
        machine =>
          (input, machine) match {
            case (_, Machine(_, 0, _)) => machine
            case (Coin, Machine(true, _, coins)) =>
              machine.copy(locked = false, coins = coins + 1)
            case (Coin, Machine(false, _, _)) => machine
            case (Turn, Machine(true, _, _))  => machine
            case (Turn, Machine(false, candies, _)) =>
              machine.copy(candies = candies - 1, locked = true)
          }

    //the challenge lies here, what if you want to work on multiple input
    def simulate(inputs: List[Input]): Machine => (Int, Int) =
      machine => {
        val listOfMachineUpdateFunction: List[Machine => Machine] = for {
          input <- inputs
        } yield dispenseMachine(input)

        @tailrec
        def loop(
            mTomFunctions: List[Machine => Machine],
            acc: Machine
        ): Machine =
          mTomFunctions match {
            case Nil     => acc
            case x :: xs => loop(xs, x(acc))
          }
        val resultMachine = loop(listOfMachineUpdateFunction, machine)

        (resultMachine.coins, resultMachine.candies)
      }

  }
  object v2 {
    //as step one we will try to abstract the function in a case class
    case class CandyMachine[A](run:Machine => (A,Machine)) {
      import CandyMachine._
      def flatMap[B](f:A => CandyMachine[B]):CandyMachine[B] =
        CandyMachine( machine => {
            val (a, machine2) = run(machine)
            f(a).run(machine2)
          }
        )
      def map[B](f:A => B):CandyMachine[B] = flatMap(a => pure(f(a)))

      def map2[B,C](cb:CandyMachine[B])(f:(A,B) => C): CandyMachine[C] =
        this.flatMap(a => cb.map(b => f(a,b)))
    }
    object CandyMachine{
      def pure[A](a:A):CandyMachine[A] = CandyMachine(m => (a,m))

      def get:CandyMachine[Machine] = CandyMachine(m => (m , m))

      def set(m:Machine):CandyMachine[Unit] = CandyMachine(_ => ((), m))

      def modify:(Machine => Machine) => CandyMachine[Unit] = f => for {
        m <- get
        _ <- set(f(m))
      } yield ()

      def sequence[A]:List[CandyMachine[A]] => CandyMachine[List[A]] =
        _.reverse.foldLeft(pure(List.empty[A]))((acc, cm) => acc.map2(cm)((a,b) => b::a))
    }
    def dispenseMachine: Input => Machine => Machine =
      input =>
        machine =>
          (input, machine) match {
            case (_, Machine(_, 0, _)) => machine
            case (Coin, Machine(true, _, coins)) =>
              machine.copy(locked = false, coins = coins + 1)
            case (Coin, Machine(false, _, _)) => machine
            case (Turn, Machine(true, _, _))  => machine
            case (Turn, Machine(false, candies, _)) =>
              machine.copy(candies = candies - 1, locked = true)
          }
    import CandyMachine._

    def simulate(inputs: List[Input]): CandyMachine[(Int, Int)] =
      for {
        _ <- sequence(inputs map (modify compose dispenseMachine))
        m <- get
      } yield (m.coins, m.candies)
  }

  object v3 {
    import cats.data._
    import cats.implicits._
    import State._
    type CandyMachine[A] = State[Machine,A]

    def dispenseMachine: Input => Machine => Machine =
      input =>
        machine =>
          (input, machine) match {
            case (_, Machine(_, 0, _)) => machine
            case (Coin, Machine(true, _, coins)) =>
              machine.copy(locked = false, coins = coins + 1)
            case (Coin, Machine(false, _, _)) => machine
            case (Turn, Machine(true, _, _))  => machine
            case (Turn, Machine(false, candies, _)) =>
              machine.copy(candies = candies - 1, locked = true)
          }

    def simulate(inputs: List[Input]): CandyMachine[(Int, Int)] =
      for {
        _ <- inputs.map(in => modify(dispenseMachine(in))).sequence
        m <- get
      } yield (m.coins, m.candies)
  }
}
