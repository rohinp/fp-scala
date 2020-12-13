package day3.Implicits.sample.dao

import day3.Implicits.sample.datasource.EmployeeDataSource
import day3.Implicits.sample.models.Employee

class EmployeeDAO()(implicit dataSource: EmployeeDataSource) {
  def getEmployeeData(): List[Employee] = {

    dataSource.fetch()
    // modifications
    // exception handling
    // log
  }
}
