package com.streese.aoc.day06

import scala.collection.mutable
import scala.util.control.Breaks.*

def part01(lines: Seq[String]): Int =
  findMarker(lines.head, 4)

def findMarker(input: String, distinctChars: Int): Int =
  var i = 0
  val last = mutable.ListBuffer.empty[Char]
  breakable {
    for c <- input do
      i += 1
      if last.size == distinctChars then
        last.dropInPlace(1)
      last.addOne(c)
      if last.size == distinctChars && last.toSet.size == last.size then
        break()
  }
  i

def part02(lines: Seq[String]): Int =
  findMarker(lines.head, 14)
