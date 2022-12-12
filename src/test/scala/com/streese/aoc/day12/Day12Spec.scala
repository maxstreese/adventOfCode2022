package com.streese.aoc.day12

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day12Spec extends PuzzlesSpec(12) {

  "parse" should "work" in {
    // solution was 339
    val paths = parse(input).pipe(calculatePaths)
    // val paths = parse(example).pipe(calculatePaths)
    val pathsReachingEnd = paths.filter(_.map(_.l).contains(Location.End))
    pprint.pprintln(pathsReachingEnd.map(_.size).min - 1)
    // pprint.pprintln(paths.head.size)
  }

  "part01" should "be correct" in {
    // println(part01(input))
    // part01(input) shouldBe ???
  }

  "part02" should "be correct" in {
    // println(part02(input))
    // part02(input) shouldBe ???
  }

}
