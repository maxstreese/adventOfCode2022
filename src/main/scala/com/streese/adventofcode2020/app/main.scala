package com.streese.adventofcode2020.app

import scala.util.Failure
import scala.util.Success
import scala.util.Try

@main def run(args: String*): Unit = Config.parse(args).foreach(run)

def run(config: Config): Unit =
  val day       = config.day
  val overwrite = config.overwrite
  def doFile(file: AocFile, exec: => Try[Unit]): Try[Unit] =
    if config.files.exists(f => f == file || f == AocFile.All) then exec
    else Success(())
  val result =
    for
      session <- parseSession(config.sessionFile)
      _       <- createDirectories(day)
      _       <- doFile(AocFile.Impl, writeImplementationFile(day, overwrite))
      _       <- doFile(AocFile.Test, writeTestFile(day, overwrite))
      _       <- doFile(AocFile.Desc, downloadDayDescription(session, day)
                   .flatMap(extractDayDescription)
                   .flatMap(text => writeDescriptionFile(day, text, overwrite)))
      _       <- doFile(AocFile.Input, downloadInput(session, day)
                   .flatMap(text => writeInputFile(day, text, overwrite)))
    yield ()
  result match
    case Failure(e) => println(e)
    case _          => println("done")
