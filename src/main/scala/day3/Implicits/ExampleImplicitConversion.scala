package day3.Implicits

import day3.Implicits.sample.datasource.{EmployeeDataSource, RemoteAPIDataSource}
import day3.Implicits.sample.models.Employee

object ExampleImplicitConversion {
  implicit def toEmployeeDataSource(
      remoteAPIDataSource: RemoteAPIDataSource
  ): EmployeeDataSource = new EmployeeDataSource {
    override def fetch(): List[Employee] = remoteAPIDataSource.call().map(_ => Employee(0, ""))
  }
}
