package day3.Implicits.sample.datasource

import day3.Implicits.sample.models.Employee

trait EmployeeDataSource {
  def fetch(): List[Employee]
}

