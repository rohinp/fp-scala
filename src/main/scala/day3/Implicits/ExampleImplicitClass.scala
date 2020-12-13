package day3.Implicits

import day3.Implicits.sample.models.Employee

object ExampleImplicitClass {
  implicit class RichEmployee(x: Employee) {
    def greet(): String = s"Hi ${x.name} : ${x.id}"
  }
}
