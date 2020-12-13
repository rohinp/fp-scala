package day3.Implicits

import day3.Implicits.sample.datasource.{
  EmployeeDataSource,
  RemoteAPIDataSource
}

object ExampleImplicitDef {
  implicit def toEmployeeDataSource(
      remoteAPIDataSource: RemoteAPIDataSource
  ): EmployeeDataSource = () => remoteAPIDataSource.call()
}
