package com.streese.aoc.day12

import scala.collection.mutable

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

def parse(lines: Seq[String]): (Point, Array[Array[Point]]) =
  val input     = lines.map(_.toArray).toArray
  val maxR      = input.length
  val maxC      = input(0).length
  var start     = Point(0, 0, Location.Start)
  val heightMap = Array.ofDim[Point](maxR, maxC)
  var (r, c)    = (0, 0)
  while r < maxR do
    c = 0
    while c < maxC do
      val char     = input(r)(c)
      val location = Location.parse(char)
      val point    = Point(r, c, location)
      if location == Location.Start then start = point
      heightMap(r)(c) = point
      c += 1
    r += 1
  start -> heightMap

def calculatePaths(start: Point, hm: Array[Array[Point]]): List[List[Point]] =
  val maxR    = hm.length
  val maxC    = hm(0).length
  val paths   = mutable.ListBuffer.empty[List[Point]]
  val visited = mutable.Map.empty[Point, Int]
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
    // if !keepGoing && newAcc.head.l == Location.End then paths.append(newAcc)
    if !keepGoing then paths.append(newAcc)
  recur(start, List.empty)
  paths.toList

def part01(input: Seq[String]) = ???

def part02(input: Seq[String]) = ???
