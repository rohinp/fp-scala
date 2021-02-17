def sum1(xs:List[Int]):Int =
  xs.foldLeft(0)((acc, v) => acc + v)

/*
not compiling
def sum[A](xs:List[A]):A =
  xs.foldLeft(0)((acc, v) => acc + v)
*/

/*trait Combine[A]{
  def combine(a:A):A
  def unit:A
}

//sub type polymorphism
def sum[A <: Combine[A]](zero:A, xs:List[A]):A =
  xs.foldLeft(zero)((acc, v) => acc.combine(v))*/

/*
trait Combine[A]{
  def combine(a:A, b:A):A
  def unit:A
}

def sum[A](xs:List[A])(c: Combine[A]):A =
  xs.foldLeft(c.unit)((acc, v) => c.combine(acc, v))

val combineString = new Combine[String] {
  override def combine(a: String, b: String) = a ++ b
  override def unit = ""
}
val combineInt = new Combine[Int] {
  override def combine(a: Int, b: Int) = a + b
  override def unit = 0
}
sum(List("a", "b"))(combineString)
sum(List(1,2,3))(combineInt)*/
//Ad-hoc polymorphism
//Abstract behaviour
trait Combine[A]{
  def combine(a:A, b:A):A
  def unit:A

  //laws
  def zeroAssociativity(a:A):Boolean =
    combine(unit, a) == combine(a, unit) && combine(a, unit) == a

  def associativity(a:A, b:A, c:A):Boolean =
    combine(combine(a,b),c) == combine(a,combine(b,c))
}

//instances
object Combine{
  //summoner
  def apply[A](implicit c:Combine[A]): Combine[A] = c

  implicit val combineString: Combine[String] = new Combine[String] {
    override def combine(a: String, b: String) = a ++ b
    override def unit = ""
  }
  implicit val combineInt: Combine[Int] = new Combine[Int] {
    override def combine(a: Int, b: Int) = a + b
    override def unit = 0
  }
}

//implicit parameters
def sum2[A](xs:List[A])(implicit c: Combine[A]):A =
  xs.foldLeft(c.unit)((acc, v) => c.combine(acc, v))

//Interface methods , context bound
def sum3[A:Combine](xs:List[A]):A = {
  val c = implicitly[Combine[A]]
  xs.foldLeft(c.unit)((acc, v) => c.combine(acc, v))
}

//syntax for combine
implicit class CombineSyntax[A:Combine](a:A){
  def |+|(b:A):A = Combine[A].combine(a,b) //implicitly[Com[A]].combine
  def zero:A = Combine[A].unit
}

def sum[A:Combine](xs:List[A]):A =
  xs.foldLeft(Combine[A].unit)((acc, v) => acc |+| v)

sum(List("a", "b"))
sum(List(1,2,3))

//laws can be tested using property based testing
Combine[Int].zeroAssociativity(1)

