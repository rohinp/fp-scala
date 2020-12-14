package day3.Implicits

import day3.Implicits.sample.datasource.{EmployeeDataSource, FileDataSource}

object ExampleImplicitParameter {
  implicit val employeeDataSource: EmployeeDataSource = new FileDataSource()
}
