package com.streese.aoc.day03

import com.streese.aoc.PuzzlesSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day03Spec extends PuzzlesSpec(3) {

  "part01" should "be correct" in {
    part01(input) shouldBe 8053
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 2425
  }

}
