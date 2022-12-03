package com.streese.adventofcode2020.day03

import scala.annotation.tailrec

/**
  * Note to self: Change List to Seq and observe that the tests will fail because
  * the pattern mach expressions will not match as expected anymore.
  */
type Compartments = (List[Int], List[Int])

def parsePriorities(lines: Seq[String]): Seq[List[Int]] =
  lines.map(_.map(_.priority).toList)

extension (c: Char)
  def priority: Int =
    if (c.isLower) c.toInt - 96
    else c.toInt - 38

def packCompartments(priorities: List[Int]): Compartments =
    priorities.splitAt(priorities.size / 2)

@tailrec
def calculateRucksack(rucksack: Compartments): Int =
  @tailrec
  def calculateItem(ah: Int, b: List[Int]): Int =
    b match
      case bh :: bt => if (ah == bh) ah else calculateItem(ah, bt)
      case _ => 0
  rucksack match
    case (ah :: at, b) =>
      val item = calculateItem(ah, b)
      if (item > 0) item else calculateRucksack(at, b)
    case _ => 0

def determineBadge(rucksacks: List[List[Int]]): Int =
  @tailrec
  def calcForList(ref: List[Int], others: List[List[Int]]): Int =
    ref match
      case h :: t =>
        val elem = calcForElem(h, others)
        if (elem > 0) elem
        else calcForList(t, others)
      case _ => 0
  def calcForElem(ref: Int, others: List[List[Int]]): Int =
    val othersWithElem =
      others
        .takeWhile(other => other.takeWhile(_ != ref).size != other.size)
        .size
    if (othersWithElem == others.size) ref
    else 0
  rucksacks match
    case ref :: others => calcForList(ref, others)
    case _ => 0

def part01(lines: Seq[String]): Int =
  parsePriorities(lines)
    .map(packCompartments)
    .map(calculateRucksack)
    .reduce(_ + _)

def part02(lines: Seq[String]): Int =
  parsePriorities(lines)
    .toList
    .grouped(3)
    .toList
    .map(determineBadge)
    .reduce(_ + _)
