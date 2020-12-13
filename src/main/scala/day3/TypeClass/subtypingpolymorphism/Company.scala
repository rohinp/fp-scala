package day3.TypeClass.subtypingpolymorphism

case class Company(name: String, strength: Int) extends MyComparable[Company] {
  override def compare(t: Company): Int = {
    if(this.strength < t.strength) -1
    else if(this.strength > t.strength) 1
    else 0
  }
}