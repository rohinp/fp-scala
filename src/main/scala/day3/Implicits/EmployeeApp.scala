package day3.Implicits
import ExampleImplicitDef._
import ExampleImplicitClass._

object EmployeeApp {
  implicit val ds: EmployeeDataSource = new RemoteAPIDataSource()
  new EmployeeDAO().getEmployeeData()

//  def greet(employee: Employee): String = s"Hi ${employee.name} : ${employee.id}"

  private val spongebob = Employee(1, "Spongebob")

//  greet(spongebob)

  spongebob.greet()
}
