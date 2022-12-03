package com.streese.adventofcode2020.day01

import scala.util.Try

def part01(lines: Seq[String]): Long =
  case class Acc(max: Long, curr: Seq[Long])
  val acc = lines.foldRight(Acc(0, Seq.empty)) { (s, acc) =>
    def ifEmpty: Acc =
      val currMax = acc.curr.reduce(_ + _)
      if (acc.max < currMax) Acc(currMax, Seq.empty) else acc.copy(curr = Seq.empty)
    def ifLong(l: Long): Acc = acc.copy(curr = acc.curr :+ l)
    Try(s.toLong).fold(_ => ifEmpty, ifLong)
  }
  val currMax = acc.curr.reduce(_ + _)
  if (acc.max < currMax) currMax else acc.max

def part02(lines: Seq[String]): Long =
  case class Acc(curr: Long, sums: Seq[Long])
  val acc = lines.foldRight(Acc(0, Seq.empty)) { (s, acc) =>
    def ifEmpty: Acc = Acc(0, acc.sums :+ acc.curr)
    def ifLong(l: Long) = acc.copy(curr = acc.curr + l)
    Try(s.toLong).fold(_ => ifEmpty, ifLong)
  }
  (acc.sums :+ acc.curr).sorted.reverse.take(3).sum
