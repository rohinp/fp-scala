package day4.futures
import day4.basics.Utilities._

object FuturesDataType extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source
  //import scala.jdk.CollectionConverters._
  import scala.util.Using
  /**
   * Important:
   * future are eager in nature
   * They evaluate as soon as apply method is called is called on future
   * <ul>
   * <li>Future has a companion object which provides the apply method</li>
   * <li>In below we could have also used a wile loop and make the calling thread busy rather than sleep</li>
   * </ul>
   *
   * */

  def readFile(fileName: String):Future[List[String]] =
    Future.fromTry{
      Using(Source.fromFile(fileName, "UTF8")){resource => resource.getLines().toList}
    }



}