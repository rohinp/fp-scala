package day3.Implicits.sample.datasource

import day3.Implicits.sample.models.Employee

class FileDataSource extends EmployeeDataSource {
  override def fetch(): List[Employee] = ???
}
