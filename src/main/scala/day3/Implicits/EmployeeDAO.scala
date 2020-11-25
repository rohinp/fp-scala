package day3.Implicits

class EmployeeDAO()(implicit dataSource: EmployeeDataSource) {
  def getEmployeeData(): List[Employee] = {

    dataSource.fetch()
    // modifications
    // exception handling
    // log
  }
}
