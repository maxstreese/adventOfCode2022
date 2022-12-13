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
  case L(elems: List[Packet])
  case N(i: Int)

object Packet:
  def parse(tokens: Seq[Token]): Packet =
    val tokensArray = tokens.toArray
    val start       = 1
    val end         = tokensArray.length - 2
    def recur(start: Int, end: Int): Packet =
      val distance      = end - start + 1
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
              startEndPairs.append((pairStart, i - 1, true))
          case _: Token.N =>
            if depth == 0 then
              startEndPairs.append((i, i, false))
        i += 1
      val elems =
        startEndPairs
          .toList
          .map { (s, e, l) =>
            val distance = e - s
            distance match
              case d if d < 0 =>
                L(List.empty)
              case d if d == 0 =>
                val n = N(tokensArray(s).asInstanceOf[Token.N].i)
                if l then L(List(n)) else n
              case d if d > 0 =>
                recur(s, e)
          }
      L(elems)
    if end - start < 0 then L(List.empty) else recur(start, end)

def part01(input: Seq[String]) = ???

def part02(input: Seq[String]) = ???
