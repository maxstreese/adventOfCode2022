package com.streese.aoc.day14

import scala.collection.mutable

case class Point(x: Int, y: Int):
  def below:      Point = Point(x    , y + 1)
  def left:       Point = Point(x - 1, y    )
  def leftBelow:  Point = Point(x - 1, y + 1)
  def right:      Point = Point(x + 1, y    )
  def rightBelow: Point = Point(x + 1, y + 1)

type RockLine = Seq[Point]

enum TileType:
  case Drop, Rock, Sand, Air

import TileType.*

case class Tile(t: TileType, ts: Int)

class Tilemap(rocks: Seq[RockLine], drops: Option[Int] = None) {

  private val dropChar = '+'
  private val rockChar = '#'
  private val sandChar = 'o'
  private val airChar  = '.'

  private val minX = rocks.flatMap(_.map(_.x)).min
  private val maxX = rocks.flatMap(_.map(_.x)).max
  private val minY = 0
  private val maxY = rocks.flatMap(_.map(_.y)).max

  private val xDist = maxX - minX + 1
  private val yDist = maxY - minY + 1

  private val dropFrom = Point(500 - minX, 0)

  private val tm = Array.fill(xDist)(Array.fill(yDist)(Tile(Air, 0)))

  tm(500 - minX)(0) = Tile(Drop, 0)

  rocks
    .map(line => line.zip(line.tail))
    .foreach(_.foreach { (a, b) =>
      tm(a.x - minX)(a.y - minY) = Tile(Rock, 0)
      val xDiff = b.x - a.x
      val yDiff = b.y - a.y
      ((0 until xDiff) ++ (xDiff until 0)).foreach { x => 
        val px = a.x + x - minX
        val py = a.y - minY
        tm(a.x + x - minX)(a.y - minY) = Tile(Rock, 0)
      }
      ((0 until yDiff) ++ (yDiff until 0)).foreach { y =>
        val px = a.x - minX
        val py = a.y + y - minY
        tm(a.x - minX)(a.y + y - minY) = Tile(Rock, 0)
      }
    })

  private def checkTile(p: Point): Option[TileType] =
    if p.x < 0 || p.x > maxX || p.y < 0 || p.y > maxY then None
    else Some(tm(p.x)(p.y).t)

  private def drop(i: Int): Boolean =
    def go(p: Point): Boolean =
      val here       = checkTile(p)
      val below      = checkTile(p.below)
      val left       = checkTile(p.left)
      val leftBelow  = checkTile(p.leftBelow)
      val right      = checkTile(p.right)
      val rightBelow = checkTile(p.rightBelow)
      if      here.isEmpty                then false
      else if below.forall(_ == Air)      then go(p.below)
      else if leftBelow.forall(_ == Air)  then go(p.leftBelow)
      else if rightBelow.forall(_ == Air) then go(p.rightBelow)
      else
        tm(p.x)(p.y) = Tile(Sand, i)
        true
    go(dropFrom)

  val sandDropped: Int =
    var i = 0
    drops match
      case None => 
        while drop(i) do
          i += 1
      case Some(max) =>
        while i < max + 1 && drop(i) do
          i += 1
    i

  def draw: String =
    val s = mutable.StringBuilder()
    var y = 0
    while y < tm(0).length do
      var x = 0
      while x < tm.length do
        val tile = tm(x)(y)
        tile.t match
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

def part01(input: Seq[String]) = ???

def part02(input: Seq[String]) = ???
