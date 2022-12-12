package com.streese.aoc.day12

import scala.collection.mutable
import scala.util.chaining.*

case class Point(r: Int, c: Int, l: Location)

extension (c: Char) def height: Int = c.toInt - 96

enum Location:
  case Start
  case End
  case Step(val h: Int)
  def height: Int = this match
    case Start   => 'a'.height
    case End     => 'z'.height
    case Step(h) => h

object Location:
  def parse(c: Char): Location =
    c match
      case 'S' => Start
      case 'E' => End
      case c   => Step(c.height)

case class Input(
  start:     Point,
  least:     Set[Point],
  heightMap: Array[Array[Point]],
)

def parse(lines: Seq[String]): Input =
  val input     = lines.map(_.toArray).toArray
  val maxR      = input.length
  val maxC      = input(0).length
  var start     = Point(0, 0, Location.Start)
  val least     = mutable.ListBuffer.empty[Point]
  val heightMap = Array.ofDim[Point](maxR, maxC)
  var (r, c)    = (0, 0)
  while r < maxR do
    c = 0
    while c < maxC do
      val char     = input(r)(c)
      val location = Location.parse(char)
      val point    = Point(r, c, location)
      if char.height == 'a'.height then least.append(point)
      if location == Location.Start then start = point
      heightMap(r)(c) = point
      c += 1
    r += 1
  Input(start, least.toSet, heightMap)

def calculatePaths(start: Point, visitedBefore: Set[Point], hm: Array[Array[Point]]): List[List[Point]] =
  val maxR    = hm.length
  val maxC    = hm(0).length
  val paths   = mutable.ListBuffer.empty[List[Point]]
  val visited = mutable.Map(visitedBefore.map(v => v -> 0).toSeq: _*)
  def recur(curr: Point, acc: List[Point]): Unit =
    val newAcc     = acc.prepended(curr)
    var keepGoing  = false
    val visitedLen = visited.getOrElse(curr, Int.MaxValue)
    val accLen     = acc.length
    if // go up
      visitedLen > accLen                                  &&
      curr.l != Location.End                               &&
      curr.r - 1 >= 0                                      &&
      hm(curr.r - 1)(curr.c).l.height <= curr.l.height + 1
    then
      visited.put(curr, accLen)
      keepGoing = true
      recur(hm(curr.r - 1)(curr.c), newAcc)
    if // go down
      visitedLen > accLen                                  &&
      curr.l != Location.End                               &&
      curr.r + 1 < maxR                                    &&
      hm(curr.r + 1)(curr.c).l.height <= curr.l.height + 1
    then
      visited.put(curr, accLen)
      keepGoing = true
      recur(hm(curr.r + 1)(curr.c), newAcc)
    if // go left
      visitedLen > accLen                                  &&
      curr.l != Location.End                               &&
      curr.c - 1 >= 0                                      &&
      hm(curr.r)(curr.c - 1).l.height <= curr.l.height + 1
    then
      visited.put(curr, accLen)
      keepGoing = true
      recur(hm(curr.r)(curr.c - 1), newAcc)
    if // go right
      visitedLen > accLen                                  &&
      curr.l != Location.End                               &&
      curr.c + 1 < maxC                                    &&
      hm(curr.r)(curr.c + 1).l.height <= curr.l.height + 1
    then
      visited.put(curr, accLen)
      keepGoing = true
      recur(hm(curr.r)(curr.c + 1), newAcc)
    if !keepGoing && newAcc.head.l == Location.End then paths.append(newAcc)
  recur(start, List.empty)
  paths.toList

def fewestSteps(paths: List[List[Point]]): Int =
  val min = paths
    .filter(_.map(_.l).contains(Location.End))
    .map(_.size)
    .min
  min - 1

def part01(input: Seq[String]): Int =
  parse(input)
    .pipe(parsedInput => calculatePaths(parsedInput.start, Set.empty, parsedInput.heightMap))
    .pipe(fewestSteps)

def part02(input: Seq[String]): Int =
  val parsedInput = parse(input)
  val seenBefore  = mutable.ListBuffer.empty[Point]
  val paths       = mutable.ListBuffer.empty[List[Point]]
  parsedInput.least.foreach { start =>
    paths.appendAll(calculatePaths(start, seenBefore.toSet, parsedInput.heightMap))
  }
  fewestSteps(paths.toList)
