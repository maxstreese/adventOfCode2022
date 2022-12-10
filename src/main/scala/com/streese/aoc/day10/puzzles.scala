package com.streese.aoc.day10

import scala.collection.mutable
import scala.util.chaining._

enum Instruction:
  case AddX(i: Int)
  case NoOp()
  def x: Int =
    this match
      case Instruction.AddX(i) => i
      case Instruction.NoOp()  => 0

object Instruction:
  def parse(line: String): Instruction =
    val kind = line.split(" ").head
    kind match
      case "noop" => NoOp()
      case "addx" => AddX(line.split(" ").last.toInt)

import Instruction.*

def xRegHist(instructions: Seq[Instruction]) =
  val queue =
    instructions.flatMap { instruction =>
      instruction match
        case i: AddX => Seq(None, Some(i))
        case i: NoOp => Seq(Some(i))
    }
  var xReg      = 1
  val xRegHist  = mutable.ListBuffer(xReg)
  queue.foreach {
    case None =>
      xRegHist.append(xReg)
    case Some(i) =>
      xReg += i.x
      xRegHist.append(xReg)
  }
  xRegHist.toSeq

def solvePart01(instructions: Seq[Instruction]) =
  xRegHist(instructions)
    .zipWithIndex
    .filter((_, i) => (i + 1 - 20) % 40 == 0 || i + 1 == 20)
    .map((x, i) => (i + 1) * x)
    .sum

def part01(lines: Seq[String]): Int =
  lines.map(Instruction.parse).pipe(solvePart01)

def solvePart02(instructions: Seq[Instruction]) =
  xRegHist(instructions)
    .grouped(40)
    .map(_.zipWithIndex)
    .take(6)
    .toList
    .map(_.map((x, i) => if (x-i).abs < 2 then "█" else " "))
    .map(_.mkString)
    .mkString("\n")

def part02(lines: Seq[String]) =
  lines.map(Instruction.parse).pipe(solvePart02)
