import dependencies._

ThisBuild / organization := "com.streese"
ThisBuild / scalaVersion := "3.2.1"
ThisBuild / version      := "0.1.0"

ThisBuild / scalacOptions ++= List(
  "-Xlint:unused",
  "-Ywarn-unused",
)

lazy val adventOfCode2020 = (project in file("."))
  .settings(
    name                 := "adventofcode2020",
    libraryDependencies ++= allDeps
  )
