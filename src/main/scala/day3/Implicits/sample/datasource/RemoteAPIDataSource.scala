package day3.Implicits.sample.datasource

// consider this to be outside of your project
case class Person()

class RemoteAPIDataSource {
  def call(): List[Person] = ???
}
