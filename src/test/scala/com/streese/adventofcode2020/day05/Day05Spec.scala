package com.streese.adventofcode2020.day05

import com.streese.adventofcode2020.PuzzlesSpec

class Day05Spec extends PuzzlesSpec(5) {

  "part01" should "be correct" in {
    part01(lines) shouldBe "TQRFCBSJJ"
  }

  "part02" should "be correct" in {
    println(part02(lines))
    // part02(lines) shouldBe ???
  }

}
