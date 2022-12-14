package com.streese.aoc.day13

import scala.collection.mutable

enum Token:
  case L
  case R
  case N(i: Int)

object Token:
  def parse(line: Seq[Char]): Seq[Token] =
    val tokens  = mutable.ListBuffer.empty[Token]
    val lineArr = line.toArray
    val maxI    = lineArr.length
    var i       = 0
    while i < maxI do
      val c = lineArr(i)
      c match
        case '[' =>
          tokens.append(L)
          i += 1
        case ']' =>
          tokens.append(R)
          i += 1
        case ',' =>
          i += 1
        case c =>
          var s = mutable.StringBuilder()
          var j = i
          while j < maxI && lineArr(j).isDigit do
            s.append(lineArr(j))
            j += 1
          tokens.append(N(s.mkString.toInt))
          i += j - i
    tokens.toSeq

enum Packet:
  case L(elems: List[Packet] = List.empty)
  case N(i: Int)
  override def toString: String =
    this match
      case Packet.L(elems) => "[" ++ elems.map(_.toString).mkString(",") ++ "]"
      case Packet.N(i) => i.toString
  private def isSmaller(l: List[Packet], r: List[Packet], i: Boolean): Boolean =
    l match
      case lh :: lt => 
        r match
          case rh :: rt => if i then lh.isSmaller(rh) else lh.isSmaller(rh) && isSmaller(lt, rt, false)
          case Nil      => false
      case Nil => true
  def isSmaller(right: Packet): Boolean =
    this match
      case Packet.L(ll) =>
        right match
          case Packet.L(lr) => isSmaller(ll, lr, false)
          case nr: Packet.N => isSmaller(ll, List(nr), true)
      case nl @ Packet.N(il) =>
        right match
          case Packet.L(lr) => isSmaller(List(nl), lr, true)
          case Packet.N(ir) => il <= ir

object Packet:
  def parse(tokens: Seq[Token]): Packet =
    val tokensArray = tokens.toArray
    val start       = 1
    val end         = tokensArray.length - 2
    def recur(start: Int, end: Int, isList: Boolean): Packet =
      val distance = end - start
      distance match
        case d if d < 0  => L()
        case d if d == 0 => if isList then L() else N(tokensArray(start).asInstanceOf[Token.N].i)
        case d if d > 0  =>
          val startEndPairs = mutable.ListBuffer.empty[(Int, Int, Boolean)]
          var pairStart     = 0
          var depth         = 0
          var i             = start
          while i <= start + distance do
            val t = tokensArray(i)
            t match
              case Token.L =>
                if depth == 0 then
                  pairStart = i + 1
                depth += 1
              case Token.R =>
                depth -= 1
                if depth == 0 then
                  startEndPairs.append((pairStart, i, true))
              case _: Token.N =>
                if depth == 0 then
                  startEndPairs.append((i, i, false))
            i += 1
          L(startEndPairs.toList.map(recur))
    val distance = end - start
    distance match
      case d if d <  0 => L()
      case d if d == 0 => L(List(recur(start, end, false)))
      case d if d > 0  => recur(start, end, true)

extension (s: String)
  def packet: Packet = Packet.parse(Token.parse(s))

case class Input(l: Packet, r: Packet):
  def compare: Boolean = l.isSmaller(r)

def parse(input: Seq[String]): Seq[Input] =
  input
    .grouped(3)
    .map(_.take(2).toList)
    .flatMap {
      case h :: t :: Nil => Some(h, t)
      case _             => None
    }
    .map((l, r) => Input(Packet.parse(Token.parse(l)), Packet.parse(Token.parse(r))))
    .toSeq

def part01(input: Seq[String]): Int =
  parse(input).map(_.compare).zipWithIndex.filter((p, _) => p).map((_, i) => i + 1).sum

def part02(input: Seq[String]) = ???
