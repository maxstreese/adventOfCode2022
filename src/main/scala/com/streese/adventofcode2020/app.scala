package com.streese.adventofcode2020

import scopt.OParser

import java.nio.file.Files
import java.nio.file.Path
import scala.jdk.CollectionConverters._
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import os.copy.over

case class Config(day: Int = -1, overwrite: Boolean = false, sessionFile: Path = Path.of("."))

object Config:

  private val defaultSessionFilePath =
    val home = System.getProperty("user.home")
    s"$home/.adventofcode.session"

  private val builder = OParser.builder[Config]

  private val parser =
    import builder._
    OParser.sequence(
      head("Set up any given day and optionally download its input. Existing files are kept."),
      help('h', "help"),
      opt[Int]('d', "day")
        .required()
        .valueName("<d>")
        .validate(x => if 1 <= x && x <= 24 then success else failure("the day needs to be between 1 and 24"))
        .action((x, c) => c.copy(day = x))
        .text("the day to set up"),
      opt[Unit]('o', "overwrite")
        .optional()
        .action((_, c) => c.copy(overwrite = true))
        .text("overwrite any existing files"),
      opt[Path]('s', "session-file")
        .required()
        .valueName("<p>")
        .withFallback(() => Path.of(defaultSessionFilePath))
        .action((x, c) => c.copy(sessionFile = x))
        .text(s"path to the session cookie file [default: $defaultSessionFilePath]")
    )

  def parse(args: Seq[String]): Option[Config] = OParser.parse(parser, args, Config())

def parseSession(sessionFile: Path): Try[String] = Try(Files.readAllLines(sessionFile).asScala.head)

lazy val sourceDirectory: os.Path = os.pwd / "src" / "main" / "scala" / "com" / "streese" / "adventofcode2020"

lazy val testDirectory: os.Path = os.pwd / "src" / "test" / "scala" / "com" / "streese" / "adventofcode2020"

lazy val resourceDirectory: os.Path = os.pwd / "src" / "test" / "resources"

def createDirectories(day: Int): Try[Unit] =
  Try {
    os.makeDir.all(sourceDirectory / f"day$day%02d")
    os.makeDir.all(testDirectory / f"day$day%02d")
    os.makeDir.all(resourceDirectory / f"day$day%02d")
  }

def writeCodeFiles(day: Int, overwrite: Boolean): Try[Unit] =
  Try {
    def cmd(target: os.Path, data: String) = if overwrite then os.write.over(target, data) else os.write(target, data)
    cmd(sourceDirectory / f"day$day%02d" / "puzzles.scala", implementationCode(day))
    cmd(testDirectory / f"day$day%02d" / f"Day$day%02dSpec.scala", testCode(day))
  }

def implementationCode(day: Int): String =
  s"""|package com.streese.adventofcode2020.day$day%02d
      |
      |def part01(lines: Seq[String]): Unit = ???
      |
      |def part02(lines: Seq[String]): Unit = ???
      |""".stripMargin

def testCode(day: Int) =
  f"""|package com.streese.adventofcode2020.day$day%02d
      |
      |import com.streese.adventofcode2020.PuzzlesSpec
      |
      |class Day${day}%02dSpec extends PuzzlesSpec($day) {
      |
      |  "part01" should "be correct" in {
      |    // println(part01(lines))
      |    // part01(lines) shouldBe ???
      |  }
      |
      |  "part02" should "be correct" in {
      |    // println(part02(lines))
      |    // part02(lines) shouldBe ???
      |  }
      |
      |}
      |""".stripMargin

@main def run(args: String*): Unit =
  Config.parse(args) match
    case None => ()
    case Some(config) => run(config)

def run(config: Config): Unit =
  val dayString = f"${config.day}%02d"
  println(os.pwd)
  val res = for {
    session <- parseSession(config.sessionFile)
    _       <- createDirectories(config.day)
    _       <- writeCodeFiles(config.day, config.overwrite)
  } yield ()
  res match
    case Failure(e) => println(e)
    case Success(_) => println("done")
