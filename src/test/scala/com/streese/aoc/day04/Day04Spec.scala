package com.streese.aoc.day04

import com.streese.aoc.PuzzlesSpec

class Day04Spec extends PuzzlesSpec(4) {

  "part01" should "be correct" in {
    part01(input) shouldBe 500
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 815
  }

}
