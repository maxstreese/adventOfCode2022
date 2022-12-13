package com.streese.aoc.day13

import scala.collection.mutable

enum Token:
  case LeftP
  case RightP
  case Comma
  case Number(i: Int)

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
          tokens.append(LeftP)
          i += 1
        case ']' =>
          tokens.append(RightP)
          i += 1
        case ',' =>
          tokens.append(Comma)
          i += 1
        case c =>
          var s = mutable.StringBuilder()
          var j = i
          while j < maxI && lineArr(j).isDigit do
            s.append(lineArr(j))
            j += 1
          tokens.append(Number(s.mkString.toInt))
          i += j - i
    tokens.toSeq

enum Packet:
  case PList(elems: List[Packet])
  case PInt(i: Int)

object Packet:
  def parse(tokens: Seq[Token]): Packet =
    def foo(rest: List[Token], acc: List[Packet]): List[Packet] =
      rest match
        case h :: t => 
          h match
            case Token.LeftP => foo(t, acc)
            case Token.RightP => acc ++ foo(t, List.empty)
            case Token.Comma => acc ++ foo(t, List.empty)
            case Token.Number(i) => (acc :+ PInt(i)) ++ foo(t, List.empty)
        case Nil => acc
    PList(foo(tokens.toList, List.empty))

def part01(input: Seq[String]) = ???

def part02(input: Seq[String]) = ???
