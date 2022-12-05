package com.streese.adventofcode2020.day05

import scala.collection.immutable.Queue

type Crates = Map[Int, List[String]]

case class Step(amount: Int, from: Int, to: Int)

def parse(lines: Seq[String]) =
  val (crates, _ :: procedure) = lines.toList.splitAt(lines.indexWhere(_.isBlank)) : @unchecked
  parseCrates(crates) -> parseProcedure(procedure)

def parseCrates(lines: Seq[String]): Crates =
  lines
    .reverse
    .tail
    .map(
      _.sliding(4, 4)
        .map(_.stripTrailing)
        .map(_.stripPrefix("["))
        .map(_.stripSuffix("]"))
        .toSeq
    )
    .transpose
    .map(_.takeWhile(s => !s.isBlank()))
    .map(_.reverse)
    .map(_.toList)
    .zipWithIndex
    .map((s, i) => (i + 1, s))
    .toMap

def parseProcedure(lines: Seq[String]) =
  lines.map { line =>
    val amount :: from :: to :: Nil = line
      .stripPrefix("move ")
      .replaceAllLiterally(" from ", ",")
      .replaceAllLiterally(" to ", ",")
      .split(",")
      .toList
      .map(_.toInt) : @unchecked
    Step(amount, from, to)
  }

def part01(lines: Seq[String]): Unit = ???

def part02(lines: Seq[String]): Unit = ???
