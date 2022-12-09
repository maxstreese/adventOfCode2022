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
  def keepInTouchWith(h: Point): Point =
    val t     = this
    val xDiff = h.x - t.x
    val yDiff = h.y - t.y
    (xDiff, yDiff) match
      case (x, y) if x <= -2 && y <= -1 => Point(t.x - 1, t.y - 1)
      case (-2,  0) => Point(t.x - 1, t.y    )
      case (x, y) if x <= -2 && y >= 1 => Point(t.x - 1, t.y + 1)
      case (x, y) if x <= -1 && y <= -2 => Point(t.x - 1, t.y - 1)
      case (x, y) if x <= -1 && y >= 2 => Point(t.x - 1, t.y + 1)
      case (0,  -2) => Point(t.x    , t.y - 1)
      case (0,   2) => Point(t.x    , t.y + 1)
      case (x, y) if x >= 1 && y <= -2 => Point(t.x + 1, t.y - 1)
      case (x, y) if x >= 1 && y >= 2 => Point(t.x + 1, t.y + 1)
      case (x, y) if x >= 2 && y <= -1 => Point(t.x + 1, t.y - 1)
      case (2,   0) => Point(t.x + 1, t.y    )
      case (x, y) if x >= 2 && y >= 1 => Point(t.x + 1, t.y + 1)
      case _        =>
        if xDiff > 2 || yDiff > 2 then
          println(xDiff)
          println(yDiff)
        t

object Point:
  def apply(): Point = Point(0, 0)

case class Rope(h: Point, t: Point):
  def move(m: Move): Rope =
    val hn = h.move(m)
    val tn = t.keepInTouchWith(hn)
    Rope(hn, tn)

object Rope:
  def apply(): Rope = Rope(Point(), Point())

opaque type ManyRope = mutable.ListBuffer[Point]

object ManyRope:
  def apply(size: Int): ManyRope =
    mutable.ListBuffer.fill(size)(Point())

extension (xs: ManyRope)
  def move(m: Move): Unit =
    val size = xs.size
    var i = 0
    while i < size do
      val x = xs(i)
      if i == 0 then xs.update(i, x.move(m))
      else xs.update(i, x.keepInTouchWith(xs(i-1)))
      i += 1
  def last: Point = xs.last

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

def part02(lines: Seq[String]): Int =
  val moves   = parse(lines)
  val rope    = ManyRope(10)
  val visited = mutable.ListBuffer(Point(0, 0))
  moves.foreach { m =>
    rope.move(m)
    visited += rope.last
  }
  visited.toSet.size
