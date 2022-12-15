package com.streese.aoc.day13

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day13Spec extends PuzzlesSpec(13) {

  "part01" should "be correct" in {
    part01(example) shouldBe 13
    part01(input) shouldBe 6187
  }

  "part02" should "be correct" in {
    part02(example) shouldBe 140
    part02(input) shouldBe 23520
  }

}
