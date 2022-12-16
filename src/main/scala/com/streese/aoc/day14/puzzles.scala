package com.streese.aoc.day14

import scala.collection.mutable
import scala.util.chaining._

case class Point(x: Int, y: Int):
  def below:      Point = Point(x    , y + 1)
  def left:       Point = Point(x - 1, y    )
  def leftBelow:  Point = Point(x - 1, y + 1)
  def right:      Point = Point(x + 1, y    )
  def rightBelow: Point = Point(x + 1, y + 1)

type RockLine = Seq[Point]

enum Tile:
  case Drop, Rock, Sand, Air

import Tile.*

class Tilemap(
  rocks:           Seq[RockLine]       ,
  drops:           Option[Int]   = None,
  usePathTracking: Boolean       = true,
  withFloor:       Boolean       = true,
) {

  private val dropChar = '+'
  private val rockChar = '#'
  private val sandChar = 'o'
  private val airChar  = '.'

  private val minX = rocks.flatMap(_.map(_.x)).min
  private val maxX = rocks.flatMap(_.map(_.x)).max
  private val maxY = rocks.flatMap(_.map(_.y)).max + (if withFloor then 2 else 0)

  val marginX     = if withFloor then maxY + 1 else 0
  val adjustmentX = -minX + marginX

  private val xDist = marginX * 2 + maxX - minX + 1
  private val yDist = maxY + 1

  private val dropFrom = Point(500 + adjustmentX, 0)

  private val tm = Array.fill(xDist)(Array.fill(yDist)(Air))

  tm(dropFrom.x)(dropFrom.y) = Drop

  private val adjustedRocks = rocks.map(_.map(p => Point(p.x + adjustmentX, p.y)))

  adjustedRocks
    .map(line => line.zip(line.tail))
    .foreach(_.foreach { (a, b) =>
      tm(a.x)(a.y) = Rock
      tm(b.x)(b.y) = Rock
      val xDiff = b.x - a.x
      val yDiff = b.y - a.y
      ((0 until xDiff) ++ (xDiff until 0)).foreach(x => tm(a.x + x)(a.y) = Rock)
      ((0 until yDiff) ++ (yDiff until 0)).foreach(y => tm(a.x)(a.y + y) = Rock)
    })

  if withFloor then (0 until xDist).foreach(x => tm(x)(maxY) = Rock)

  private val path = mutable.ListBuffer.empty[Point]

  private def checkTile(p: Point): Option[Tile] =
    if p.x < 0 || p.x > maxX || p.y < 0 || p.y > maxY then None
    else Some(tm(p.x)(p.y))

  private def drop: Boolean =
    def go(p: Point): Boolean =
      val here       = checkTile(p)
      val below      = checkTile(p.below)
      val left       = checkTile(p.left)
      val leftBelow  = checkTile(p.leftBelow)
      val right      = checkTile(p.right)
      val rightBelow = checkTile(p.rightBelow)
      if      here.isEmpty                then false
      else if here.exists(_ == Sand)      then false
      else if below.forall(_ == Air)      then { if usePathTracking then path.prepend(p); go(p.below)      }
      else if leftBelow.forall(_ == Air)  then { if usePathTracking then path.prepend(p); go(p.leftBelow)  }
      else if rightBelow.forall(_ == Air) then { if usePathTracking then path.prepend(p); go(p.rightBelow) }
      else                                     { tm(p.x)(p.y) = Sand; true }
    val start = if usePathTracking && path.length > 0 then path.remove(0) else dropFrom
    go(start)

  val sandDropped: Int =
    var i = 0
    drops match
      case None => 
        while drop do
          i += 1
      case Some(max) =>
        while i < max && drop do
          i += 1
    i

  def draw: String =
    val s = mutable.StringBuilder()
    var y = 0
    while y < tm(0).length do
      var x = 0
      while x < tm.length do
        val tile = tm(x)(y)
        tile match
          case Drop => s.append(dropChar)
          case Rock => s.append(rockChar)
          case Sand => s.append(sandChar)
          case Air  => s.append(airChar)
        x += 1
      s.append('\n')
      y += 1
    s.mkString

}

def parse(input: Seq[String]): Seq[RockLine] =
  input.map { line =>
    line
      .split(" -> ")
      .map { rawP =>
        val x :: y :: Nil = rawP.split(",").toList : @unchecked
        Point(x.toInt, y.toInt)
      }
    }

def part01(input: Seq[String]): Int =
  parse(input).pipe(Tilemap(_, withFloor = false)).sandDropped

def part02(input: Seq[String]): Int =
  parse(input).pipe(Tilemap(_, withFloor = true)).sandDropped
