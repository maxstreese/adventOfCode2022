package com.streese.aoc.day05

import scala.collection.immutable.Queue

type Stacks = Map[Int, List[String]]

case class Step(amount: Int, from: Int, to: Int)

def parse(lines: Seq[String]) =
  val (stacks, _ :: procedure) = lines.toList.splitAt(lines.indexWhere(_.isBlank)) : @unchecked
  parseStacks(stacks) -> parseProcedure(procedure)

def parseStacks(lines: Seq[String]): Stacks =
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

def unload(stacks: Stacks, procedure: Seq[Step], reverse: Boolean): Stacks =
  procedure.foldLeft(stacks) { (stacks, step) =>
    val toTransfer = stacks.get(step.from).toList.flatMap(_.take(step.amount))
    val toTransferInOrder = if reverse then toTransfer.reverse else toTransfer
    stacks
      .updatedWith(step.from)(_.map(_.drop(step.amount)))
      .updatedWith(step.to)(_.map(_.prependedAll(toTransferInOrder)))
  }

def topOfStacks(stacks: Stacks): String =
  stacks
    .toList
    .sortBy((i, _) => i)
    .map((_, stack) => stack)
    .map(_.headOption)
    .flatten
    .mkString

def part01(lines: Seq[String]): String =
  val (stacks, procedure) = parse(lines)
  val unloadedStacks = unload(stacks, procedure, reverse = true)
  topOfStacks(unloadedStacks)

def part02(lines: Seq[String]): String =
  val (stacks, procedure) = parse(lines)
  val unloadedStacks = unload(stacks, procedure, reverse = false)
  topOfStacks(unloadedStacks)
