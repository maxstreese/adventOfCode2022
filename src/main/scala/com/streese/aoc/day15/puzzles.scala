package com.streese.aoc.day15

import scala.util.chaining._

case class Point(x: Int, y: Int)

case class Interval(from: Int, to: Int):
  def combine(other: Interval): Option[Interval] =
    val min = from min other.from
    val max = to max other.to
    if      from <= other.from && to >= other.from then Some(Interval(min, max))
    else if other.from <= from && other.to >= to   then Some(Interval(min, max))
    else None
  def taken: Int = (to - from).abs + 1
  def contains(i: Int): Boolean = i >= from && i <= to

case class Sensor(s: Point, b: Point):

  val manhatten: Int = (s.x - b.x).abs + (s.y - b.y).abs

  def withinManhatten(b: Point) = Sensor(s, b).manhatten <= manhatten

  def withinManhatten(y: Int): Option[Interval] =
    val available = manhatten - (y.abs - s.y.abs).abs
    if available >= 0 then Some(Interval(s.x - available, s.x + available)) else None

end Sensor

def parse(lines: Seq[String]): Seq[Sensor] =
  lines.map { line =>
    val parts = line
      .stripPrefix("Sensor at x=")
      .replace(": closest beacon is at x=", ",")
      .replace(" y=", "")
      .split(",")
      .map(_.toInt)
      .toList
    val sx :: sy :: bx :: by :: Nil = parts : @unchecked
    Sensor(Point(sx, sy), Point(bx, by))
  }

def withinManhattenInY(y: Int, sensors: Seq[Sensor]): Seq[Interval] =
  sensors
    .flatMap(_.withinManhatten(y))
    .sortBy(_.from)
    .foldLeft((List.empty[Interval])) { (acc, a) =>
      acc match
        case h :: t =>
          h.combine(a) match
            case None => a :: h :: t
            case Some(combined) => combined :: t
        case Nil => List(a)
    }

def beaconsInY(y: Int, sensors: Seq[Sensor]): Set[Int] = sensors.map(_.b).filter(_.y == y).map(_.x).toSet

def solvePart01(y: Int, sensors: Seq[Sensor]) =
  val taken   = withinManhattenInY(y, sensors)
  val beacons = beaconsInY(y, sensors).filter(b => taken.exists(_.contains(b))).size
  taken.foldLeft(0)((acc, a) => acc + a.taken) - beacons

def part01(input: Seq[String]): Int = solvePart01(2000000, parse(input))

def part02(input: Seq[String]) = ???
