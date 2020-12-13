package day3.Implicits

import day3.Implicits.sample.datasource.{EmployeeDataSource, FileDataSource}

object ExampleImplicitVal {
  implicit val dd: EmployeeDataSource = new FileDataSource()
}
