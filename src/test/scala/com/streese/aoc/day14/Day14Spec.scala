package com.streese.aoc.day14

import com.streese.aoc.PuzzlesSpec

import scala.util.chaining._

class Day14Spec extends PuzzlesSpec(14) {

  "part01" should "be correct" in {
    part01(input) shouldBe 638
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 31722
  }

}
