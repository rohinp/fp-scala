package day3.Implicits.sample.datasource

import day3.Implicits.sample.models.Employee

class CassandraDataSource extends EmployeeDataSource {
  override def fetch(): List[Employee] = ???
}

