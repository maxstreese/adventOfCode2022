package com.streese.aoc.day13

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day13Spec extends PuzzlesSpec(13) {

  "something" should "work" in {
    // val (l, r) = ("[1,1,3,1,1]".packet, "[1,1,5,1,1]".packet)
    val (l, r) = ("[[1],[2,3,4]]".packet, "[[1],4]".packet)
    // val (l, r) = ("[1]".packet, "[4,1]".packet)
    println(l.isSmaller(r))
    // parse(example).map(_.compare).foreach(println)
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
