package day3.Implicits

import day3.Implicits.sample.datasource.{EmployeeDataSource, FileDataSource}

object ExampleImplicitVal {
  implicit val employeeDataSource: EmployeeDataSource = new FileDataSource()
}
