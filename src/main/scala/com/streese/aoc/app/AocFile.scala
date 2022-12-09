package com.streese.aoc.app

enum AocFile(val cli: String):
  case All   extends AocFile("all")
  case Desc  extends AocFile("desc")
  case Input extends AocFile("input")
  case Test  extends AocFile("test")
  case Impl  extends AocFile("impl")

object AocFile:
  given scoptReader: scopt.Read[AocFile] = scopt.Read.reads { s =>
    AocFile.values.find(f => f.cli == s).get
  }
  val cliDescription: String =
    "possible values: " + AocFile.values.map(_.cli).mkString(",") + s" [default: ${All.cli}]"
