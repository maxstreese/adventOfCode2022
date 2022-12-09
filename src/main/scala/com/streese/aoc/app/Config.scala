package com.streese.aoc.app

import scopt.OParser

import java.nio.file.Path

case class Config(
  day:         Int          = -1,
  files:       Seq[AocFile] = Seq(AocFile.All),
  overwrite:   Boolean      = false,
  sessionFile: Path         = Path.of(".")
)

object Config:

  private val defaultSessionFilePath =
    val home = System.getProperty("user.home")
    s"$home/.adventofcode.session"

  private val builder = OParser.builder[Config]

  private val parser =
    import builder._
    import AocFile.given
    OParser.sequence(
      head("Set up any given day and optionally download its input. Existing files are kept."),
      help('h', "help"),
      opt[Int]('d', "day")
        .required()
        .valueName("<d>")
        .validate(x => if 1 <= x && x <= 24 then success else failure("the day needs to be between 1 and 24"))
        .action((x, c) => c.copy(day = x))
        .text("the day to set up"),
      opt[Seq[AocFile]]('f', "files")
        .required()
        .valueName("<d>")
        .withFallback(() => Seq(AocFile.All))
        .action((x, c) => c.copy(files = x))
        .text(AocFile.cliDescription),
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

  def parse(args: Seq[String]): Option[Config] =
    OParser.parse(parser, args, Config())
