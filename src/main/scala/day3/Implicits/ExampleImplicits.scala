package day3.Implicits

object ExampleImplicitVal {
  implicit val dd: EmployeeDataSource = new FileDataSource()
}

object ExampleImplicitDef {
  implicit def xx(remoteAPIDataSource: RemoteAPIDataSource): EmployeeDataSource = () => remoteAPIDataSource.call()
}

object ExampleImplicitClass {
  implicit class MyCLass(x: Employee) {
    def greet(): String = s"Hi ${x.name} : ${x.id}"
  }
}
