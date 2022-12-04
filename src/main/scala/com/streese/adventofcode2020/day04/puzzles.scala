package com.streese.adventofcode2020.day04

case class Range(min: Int, max: Int)

case class Pair(a: Range, b: Range):
  def overlapFully: Boolean =
    (a.min <= b.min && a.max >= b.max) || (b.min <= a.min && b.max >= a.max)
  def overlapPartially: Boolean =
    (a.min <= b.min && a.max >= b.min) || (b.min <= a.min && b.max >= a.min)

def parsePairs(lines: Seq[String]): Seq[Pair] =
  lines.map { line =>
    val a :: b :: Nil = line.split(",").toList : @unchecked
    Pair(parseRange(a), parseRange(b))
  }

def parseRange(s: String): Range =
  val min :: max :: Nil = s.split("-").toList : @unchecked
  Range(min.toInt, max.toInt)

def part01(lines: Seq[String]): Int =
  parsePairs(lines)
    .map(_.overlapFully)
    .filter(identity)
    .size

def part02(lines: Seq[String]): Int =
  parsePairs(lines)
    .map(_.overlapPartially)
    .filter(identity)
    .size
