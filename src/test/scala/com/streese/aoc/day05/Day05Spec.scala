package com.streese.aoc.day05

import com.streese.aoc.PuzzlesSpec

class Day05Spec extends PuzzlesSpec(5) {

  "part01" should "be correct" in {
    part01(lines) shouldBe "TQRFCBSJJ"
  }

  "part02" should "be correct" in {
    part02(lines) shouldBe "RMHFJNVFP"
  }

}
