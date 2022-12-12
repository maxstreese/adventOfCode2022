package com.streese.aoc.day12

import com.streese.aoc.PuzzlesSpec

import scala.util.chaining.*

class Day12Spec extends PuzzlesSpec(12) {

  "part01" should "be correct" in {
    part01(example) shouldBe 31
    part01(input) shouldBe 339
  }

  "part02" should "be correct" in {
    part02(example) shouldBe 29
    part02(input) shouldBe 332
  }

}
