import sbt._
import Keys._

object dependencies {

  private def compileScope(modules: ModuleID*): Seq[ModuleID] = Seq(modules: _*)

  private def testScope(modules: ModuleID*): Seq[ModuleID] = Seq(modules: _*).map(_ % "test")

  object versions {
    val osLib     = "0.8.1"
    val scalatest = "3.2.14"
  }

  object libs {
    val osLib     = "com.lihaoyi"   %% "os-lib"    % versions.osLib
    val scalatest = "org.scalatest" %% "scalatest" % versions.scalatest
  }

  val allDeps: Seq[ModuleID] = testScope(libs.osLib, libs.scalatest)

}
