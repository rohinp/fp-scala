package day3.Implicits.sample

import day3.Implicits.ExampleImplicitParameter._
import day3.Implicits.ExampleImplicitClass._
//import day3.Implicits.ExampleImplicitConversion._
import day3.Implicits.sample.dao.EmployeeDAO
import day3.Implicits.sample.datasource.{EmployeeDataSource, RemoteAPIDataSource}
import day3.Implicits.sample.models.Employee

object EmployeeApp extends App {
//  implicit val ds: EmployeeDataSource = new RemoteAPIDataSource()
    new EmployeeDAO().getEmployeeData()

  def greet(employee: Employee): String =
    s"Hi ${employee.name} : ${employee.id}"

  private val spongebob = Employee(1, "Spongebob")
  greet(spongebob)

  spongebob.greet()
}
