package com.streese.adventofcode2020.app

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import requests.Response

import java.nio.file.Files
import java.nio.file.Path
import scala.jdk.CollectionConverters._
import scala.util.Try

def parseSession(sessionFile: Path): Try[String] = Try(Files.readAllLines(sessionFile).asScala.head)

def downloadDayDescription(session: String, day: Int): Try[String] =
  Try {
    val headers = Map("content-type" -> "text/html", "cookie" -> s"session=$session")
    requests.get(s"https://adventofcode.com/2022/day/$day", headers = headers).text()
  }

def downloadInput(session: String, day: Int): Try[String] =
  Try {
    val headers = Map("content-type" -> "text/plain", "cookie" -> s"session=$session")
    requests.get(s"https://adventofcode.com/2022/day/$day/input", headers = headers).text()
  }

def extractDayDescription(text: String) =
  Try {
    val desc = Jsoup
      .parse(text)
      .body
      .getElementsByClass("day-desc")
      .asScala
      .head
    FlexmarkHtmlConverter.builder.build.convert(desc)
  }
