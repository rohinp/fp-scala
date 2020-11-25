object extensionMethods {
  implicit class ColdSyntaxForList[A](a:List[A]) {
    def cold:List[String] = a.map(_.toString)
  }

  List(1,2,3).cold
}