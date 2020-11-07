name := "fp-scala"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.3"

initialCommands in Compile in console :=
  """
    |import cats._
    |import cats.data._
    |import cats.implicits._
    |import day1.Functions._
    |import day1.ParserCombinator.Parser._
    |""".stripMargin

scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  //"-Xlint",               // enable handy linter warnings
  //"-Xfatal-warnings",      // turn compiler warnings into errors
  "-language:implicitConversions"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0" withSources(),
  "org.typelevel" %% "cats-effect" % "2.2.0" withSources(),
  "co.fs2" %% "fs2-core" % "2.4.4" withSources(),
  "org.scalatest" %% "scalatest" % "3.2.0" % Test,
  "org.scalatest" %% "scalatest-wordspec" % "3.2.0" % Test
)

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
