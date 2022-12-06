import sbt._
import Keys._

object dependencies {

  private def compileScope(modules: ModuleID*): Seq[ModuleID] = Seq(modules: _*)

  private def testScope(modules: ModuleID*): Seq[ModuleID] = Seq(modules: _*).map(_ % "test")

  object versions {
    val html2md   = "0.64.0"
    val jsoup     = "1.15.3"
    val osLib     = "0.8.1"
    val requests  = "0.7.1"
    val scalatest = "3.2.14"
    val scopt     = "4.1.0"
  }

  object libs {
    val html2md   = "com.vladsch.flexmark"  % "flexmark-html2md-converter" % versions.html2md
    val jsoup     = "org.jsoup"             % "jsoup"                      % versions.jsoup
    val osLib     = "com.lihaoyi"          %% "os-lib"                     % versions.osLib
    val requests  = "com.lihaoyi"          %% "requests"                   % versions.requests
    val scalatest = "org.scalatest"        %% "scalatest"                  % versions.scalatest
    val scopt     = "com.github.scopt"     %% "scopt"                      % versions.scopt
  }

  val allDeps: Seq[ModuleID] =
    compileScope(libs.html2md, libs.jsoup, libs.osLib, libs.requests, libs.scopt) ++
    testScope(libs.scalatest)

}
