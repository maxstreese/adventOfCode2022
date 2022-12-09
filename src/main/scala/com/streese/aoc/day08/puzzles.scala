package com.streese.aoc.day08

import scala.collection.mutable
import scala.util.chaining._

def parse(lines: Seq[String]): List[List[Int]] =
  lines.map(_.map(_.toString.toInt).toList).toList

def calcVis(in: List[List[Int]]): List[List[Boolean]] =
  in.map { xs =>
    var max = -1
    xs.map { x =>
      if x > max then
        max = x
        true
      else false
    }
  } 

def combine[T](a: List[List[T]], b: List[List[T]], f: ((T, T)) => T): List[List[T]] =
  a.zip(b).map(_.zip(_).map(f))

def combineVis(a: List[List[Boolean]], b: List[List[Boolean]]): List[List[Boolean]] =
  combine(a, b, _ || _)

def part01(lines: Seq[String]): Int =
  val ls = parse(lines)
  val rs = ls.map(_.reverse)
  val ts = ls.transpose
  val bs = ls.transpose.map(_.reverse)
  val lsVis = calcVis(ls)
  val rsVis = calcVis(rs).map(_.reverse)
  val tsVis = calcVis(ts).transpose
  val bsVis = calcVis(bs).map(_.reverse).transpose
  val combinedVis =
    combineVis(lsVis, rsVis)
      .pipe(combineVis(_, tsVis))
      .pipe(combineVis(_, bsVis))
  combinedVis.foldLeft(0)((n, xs) => n + xs.filter(identity).length)

def calcScenic(in: List[List[Int]]): List[List[Int]] =
  in.map { xs =>
    val seen = mutable.ListBuffer.empty[Int]
    xs.map { x =>
      val scenic =
        val smaller = seen.reverse.takeWhile(_ < x)
        smaller.length + (if seen.size == smaller.length then 0 else 1)
      seen.append(x)
      scenic
    }
  }

def combineScenic(a: List[List[Int]], b: List[List[Int]]): List[List[Int]] =
  combine(a, b, _ * _)

def part02(lines: Seq[String]): Int =
  val ls = parse(lines)
  val rs = ls.map(_.reverse)
  val ts = ls.transpose
  val bs = ls.transpose.map(_.reverse)
  val lsScenic = calcScenic(ls)
  val rsScenic = calcScenic(rs).map(_.reverse)
  val tsScenic = calcScenic(ts).transpose
  val bsScenic = calcScenic(bs).map(_.reverse).transpose
  val combinedScenic =
    combineScenic(lsScenic, rsScenic)
      .pipe(combineScenic(_, tsScenic))
      .pipe(combineScenic(_, bsScenic))
  combinedScenic.foldLeft(0)((n, xs) => n max xs.max)
