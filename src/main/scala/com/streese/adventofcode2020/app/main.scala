package com.streese.adventofcode2020.app

import scala.util.Failure

@main def run(args: String*): Unit = Config.parse(args).foreach(run)

def run(config: Config): Unit =
  val day       = config.day
  val overwrite = config.overwrite
  val result =
    for
      session <- parseSession(config.sessionFile)
      _       <- createDirectories(day)
      _       <- writeImplementationFile(day, overwrite)
      _       <- writeTestFile(day, overwrite)
      _       <- downloadDayDescription(session, day).flatMap(text => writeDescriptionFile(day, text, overwrite))
      _       <- downloadInput(session, day).flatMap(text => writeInputFile(day, text, overwrite))
    yield ()
  result match
    case Failure(e) => println(e)
    case _          => println("done")
