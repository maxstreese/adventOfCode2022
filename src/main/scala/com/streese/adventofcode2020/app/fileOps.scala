package com.streese.adventofcode2020.app

import scala.jdk.CollectionConverters._
import scala.util.Try

lazy val implementationDirectory: os.Path =
  os.pwd / "src" / "main" / "scala" / "com" / "streese" / "adventofcode2020"

lazy val testDirectory: os.Path =
  os.pwd / "src" / "test" / "scala" / "com" / "streese" / "adventofcode2020"

lazy val resourceDirectory: os.Path =
  os.pwd / "src" / "test" / "resources"

def createDirectories(day: Int): Try[Unit] =
  Try {
    os.makeDir.all(implementationDirectory / f"day$day%02d")
    os.makeDir.all(testDirectory / f"day$day%02d")
    os.makeDir.all(resourceDirectory / f"day$day%02d")
  }

def writeFile(base: os.Path, day: Int, name: String, text: String, overwrite: Boolean): Try[Unit] =
  Try {
    def cmd(target: os.Path, data: String) = if overwrite then os.write.over(target, data) else os.write(target, data)
    cmd(base / f"day$day%02d" / name, text)
  }

def writeDescriptionFile(day: Int, text: String, overwrite: Boolean): Try[Unit] =
  writeFile(resourceDirectory, day, "readme.md", text, overwrite)

def writeInputFile(day: Int, text: String, overwrite: Boolean): Try[Unit] =
  writeFile(resourceDirectory, day, "input.txt", text, overwrite)

def writeImplementationFile(day: Int, overwrite: Boolean): Try[Unit] =
  writeFile(implementationDirectory, day, "puzzles.scala", implementationCode(day), overwrite)

def writeTestFile(day: Int, overwrite: Boolean): Try[Unit] =
  writeFile(testDirectory, day, f"Day$day%02dSpec.scala", testCode(day), overwrite)
