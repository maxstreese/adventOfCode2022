package com.streese.aoc.day11

import scala.collection.mutable
import scala.math.BigInt
import scala.util.chaining._

enum Expr:
  case Add(x: Expr, y: Expr)
  case Mul(x: Expr, y: Expr)
  case Var(x: BigInt)
  case Old()
  def calc(old: BigInt): BigInt = this match
    case Expr.Add(x, y) => x.calc(old) + y.calc(old)
    case Expr.Mul(x, y) => x.calc(old) * y.calc(old)
    case Expr.Var(x)    => x
    case Expr.Old()     => old

object Expr:
  def parse(line: String): Expr =
    val stripped = line.stripLeading.stripPrefix("Operation: new = ")
    val x :: o :: y :: Nil = stripped.split(" ").toList : @unchecked
    val xExpr = if x == "old" then Old() else Var(x.toLong)
    val yExpr = if y == "old" then Old() else Var(y.toLong)
    if o == "+" then Add(xExpr, yExpr) else Mul(xExpr, yExpr)

case class Monkey(
  items:     List[BigInt],
  operation: Expr,
  divisor:   BigInt,
  ifTrue:    Int,
  ifFalse:   Int,
  inspected: BigInt = 0,
)

object Monkey:
  def parse(lines: Seq[String]): Monkey =
    val items =
      lines(1)
        .stripLeading
        .stripPrefix("Starting items: ")
        .split(", ")
        .map(_.toLong)
        .map(BigInt.apply)
        .toList
    val operation =
      Expr.parse(lines(2))
    val divisor = lines(3)
        .stripLeading
        .stripPrefix("Test: divisible by ")
        .toLong
    val ifTrue =
      lines(4)
        .stripLeading
        .stripPrefix("If true: throw to monkey ")
        .toInt
    val ifFalse =
      lines(5)
        .stripLeading
        .stripPrefix("If false: throw to monkey ")
        .toInt
    Monkey(items, operation, divisor, ifTrue, ifFalse)

def parse(lines: Seq[String]): mutable.SortedMap[Int, Monkey] =
  lines
    .grouped(7)
    .map(Monkey.parse)
    .zipWithIndex
    .map((m, i) => i -> m)
    .toList
    .pipe(x => mutable.SortedMap(x: _*))

def lcm(xs: List[BigInt]): BigInt = xs.reduce(lcm)

def lcm(a: BigInt, b: BigInt): BigInt = a * b / gcd(a, b)

def gcd(a: BigInt, b: BigInt): BigInt = if b == 0 then a else gcd(b, a % b)

def simulateRounds(
  rounds:  Int,
  monkeys: mutable.SortedMap[Int, Monkey],
  adjust:  BigInt => BigInt
): Seq[Map[Int, Monkey]] =
  (0 until rounds).map { _ =>
    for (i, monkey) <- monkeys do
      monkey.items.foreach { item =>
        monkeys(i) = monkey.copy(items = List.empty, inspected = monkey.inspected + monkey.items.length)
        val updatedItem     = adjust(monkey.operation.calc(item))
        val check           = updatedItem % monkey.divisor == 0
        val iToSendTo       = if check then monkey.ifTrue else monkey.ifFalse
        val toSendTo        = monkeys(iToSendTo)
        val toSendToUpdated = toSendTo.copy(items = toSendTo.items.appended(updatedItem))
        monkeys.update(iToSendTo, toSendToUpdated)
      }
    monkeys.toMap
  }

def solve(n: Int, monkeys: mutable.SortedMap[Int, Monkey], adjust: BigInt => BigInt): BigInt =
  simulateRounds(n, monkeys, adjust)
    .last
    .values
    .map(_.inspected)
    .toSeq
    .sorted
    .reverse
    .take(2)
    .reduce(_ * _)

def part01(input: Seq[String]): BigInt =
  parse(input).pipe(solve(20, _, _ / 3L))

def part02(input: Seq[String]): BigInt =
  val monkeys = parse(input)
  val checkLcm = lcm(monkeys.values.map(_.divisor).toList)
  solve(10000, monkeys, _ % checkLcm)
