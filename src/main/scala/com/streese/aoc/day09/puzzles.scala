package com.streese.aoc.day09

import scala.collection.mutable

enum Move(val c: Char):
  case Left  extends Move('L')
  case Right extends Move('R')
  case Up    extends Move('U')
  case Down  extends Move('D')

case class Point(x: Int, y: Int):
  def move(m: Move): Point = m match
    case Move.Left  => Point(x - 1, y    )
    case Move.Right => Point(x + 1, y    )
    case Move.Up    => Point(x    , y + 1)
    case Move.Down  => Point(x    , y - 1)

object Point:
  def apply(): Point = Point(0, 0)

case class Rope(h: Point, t: Point):
  def calcT(hn: Point): Point =
    val xDiff = hn.x - t.x
    val yDiff = hn.y - t.y
    (xDiff, yDiff) match
      case (-2, -1) => Point(t.x - 1, t.y - 1)
      case (-2,  0) => Point(t.x - 1, t.y    )
      case (-2,  1) => Point(t.x - 1, t.y + 1)
      case (-1, -2) => Point(t.x - 1, t.y - 1)
      case (-1,  2) => Point(t.x - 1, t.y + 1)
      case (0,  -2) => Point(t.x    , t.y - 1)
      case (0,   2) => Point(t.x    , t.y + 1)
      case (1,  -2) => Point(t.x + 1, t.y - 1)
      case (1,   2) => Point(t.x + 1, t.y + 1)
      case (2,  -1) => Point(t.x + 1, t.y - 1)
      case (2,   0) => Point(t.x + 1, t.y    )
      case (2,   1) => Point(t.x + 1, t.y + 1)
      case _        => t
  def move(m: Move): Rope =
    val hn = h.move(m)
    val tn = calcT(hn)
    Rope(hn, tn)

object Rope:
  def apply(): Rope = Rope(Point(), Point())

object Move:
  def parse(s: String): Seq[Move] =
    val move = Move.values.find(_.c == s.head).get
    val times = s.drop(2).toInt
    Seq.fill(times)(move)

def parse(lines: Seq[String]): Seq[Move] =
  lines.map(Move.parse).flatten

def part01(lines: Seq[String]): Int =
  val moves   = parse(lines)
  var rope    = Rope()
  val visited = mutable.ListBuffer(Point(0, 0))
  moves.foreach { m =>
    rope     = rope.move(m)
    visited += rope.t
  }
  visited.toSet.size

def part02(lines: Seq[String]): Unit = ???
