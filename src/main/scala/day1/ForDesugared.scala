package day1

object ForDesugared {

  val gen1: Seq[Int] = 1 to 5
  val gen2: Seq[Int] = 5 to 10
  //simple for expression
  for {
    x <- gen1
  } yield x
  //desugared code
  gen1.map(identity)

  //Lets add a variable assignment in for
  for {
    x <- gen1
    y = x * x
  } yield x + y

  /**
   * The desugared version :
   * 1.The values from the generator and the assignment operator are mapped into a tuple
   * 2.Now this acts as our new generator of tuples on which the actual operations are performed
   */
  gen1.map(x => {
    val y = x * x
    (x, y)
  }).map({
    case (x @ _, y @ _) => x + y
  })


  //for expression with two generators
  for {
    x <- gen1
    y <- gen2
  } yield x * y
  //desugared code
  gen1.flatMap(x => gen2.map(y => x * y))

  //for expression with two generators and back to back assignments
  for {
    x <- gen1
    x1 = x + 1
    y <- gen2
    y1 = y + 1
  } yield x1 * y1

  /*
  * The desugared version
  * 1.The values from the generator and the assignment operator are mapped into a tuple
  * 2.Now this acts as our new generator of tuples on which the flatMap is performed as there are multiple generators
  * 3. Similar map happens with gen2 in order to form tuple with assignment on y1
  * 4. And in the end last map for the result computation
  * */
  gen1.map(x => {
    val x1 = x + 1
    (x, x1)
  }).flatMap[Int]({
    case (x @ _, x1 @ _) => gen2.map((y: Int) => {
      val y1 = y + 1
      (y, y1)
    }).map[Int]({
      case (y @ _, y1 @ _) => x1 * y1
    })
  })

  //lets put one more for the three generators
  for {
    x <- gen1
    y <- gen1
    z <- gen1
  } yield x * y * z
  //desugared code for three generators
  gen1.flatMap(x => gen1.flatMap(y => gen1.map(z => x * y * z)))

}
