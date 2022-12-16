package com.streese.aoc.day15

import scala.util.chaining._

case class Point(x: Int, y: Int)

case class Sensor(s: Point, b: Point):

  val manhatten: Int = (s.x - b.x).abs + (s.y - b.y).abs

  def withinManhatten(b: Point) = Sensor(s, b).manhatten <= manhatten

  def withinManhatten(y: Int): Set[Point] =
    val available = manhatten - (y.abs - s.y.abs).abs
    val l = (s.x to s.x + available).map(x => Point(x, y)).toSet
    val r = (s.x - available to s.x).map(x => Point(x, y)).toSet
    l ++ r

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

def takenInYNotGoodButSomehowFaster(y: Int, sensors: Seq[Sensor]): Int =
  val beacons = sensors.map(_.b).toSet
  val minX    = sensors.map(_.s.x).min min sensors.map(_.b.x).min - 3500000
  val maxX    = sensors.map(_.s.x).max max sensors.map(_.b.x).max + 3500000
  (minX to maxX)
    .count { x =>
      val p = Point(x, y)
      !beacons.exists(_ == p) && sensors.exists(_.withinManhatten(p))
    }

def takenInYSmartButNotReallyBecauseSlow(y: Int, sensors: Seq[Sensor]): Int =
  val beacons = sensors.map(_.b).toSet
  sensors.flatMap(_.withinManhatten(y)).toSet.diff(beacons).size

def part01(input: Seq[String]): Int =
  parse(input).pipe(takenInYSmartButNotReallyBecauseSlow(2000000, _))

def part02(input: Seq[String]) = ???
