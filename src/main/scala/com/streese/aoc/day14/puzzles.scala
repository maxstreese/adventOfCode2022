package com.streese.aoc.day14

import scala.collection.mutable

case class Point(x: Int, y: Int)

type RockLine = Seq[Point]

enum TileType:
  case Rock, Sand, Air

import TileType.*

case class Tile(t: TileType, ts: Int)

class Tilemap(rocks: Seq[RockLine]) {

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
  
  def draw(at: Int): String =
    val s = mutable.StringBuilder()
    var y = 0
    while y < tm(0).length do
      var x = 0
      while x < tm.length do
        val tile = tm(x)(y)
        tile.t match
          case Rock => s.append(rockChar)
          case Sand => if tile.ts <= at then s.append(sandChar) else s.append(airChar)
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
