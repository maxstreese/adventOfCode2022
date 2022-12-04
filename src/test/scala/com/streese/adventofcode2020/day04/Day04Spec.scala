package com.streese.adventofcode2020.day04

import com.streese.adventofcode2020.PuzzlesSpec

class Day04Spec extends PuzzlesSpec(4) {

  "part01" should "be correct" in {
    part01(lines) shouldBe 500
  }

  "part02" should "be correct" in {
    part02(lines) shouldBe 815
  }

}
