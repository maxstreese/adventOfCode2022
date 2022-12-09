package com.streese.aoc.day08

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day08Spec extends PuzzlesSpec(8) {

  "part01" should "be correct" in {
    part01(input) shouldBe 1779
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 172224
  }

}
