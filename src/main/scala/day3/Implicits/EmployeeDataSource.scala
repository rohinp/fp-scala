package day3.Implicits

trait EmployeeDataSource {
  def fetch(): List[Employee]
}

class FileDataSource extends EmployeeDataSource {
  override def fetch(): List[Employee] = ???
}

class CassandraDataSource extends EmployeeDataSource {
  override def fetch(): List[Employee] = ???
}

