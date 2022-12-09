package com.streese.aoc.day01

import com.streese.aoc.PuzzlesSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day01Spec extends PuzzlesSpec(1) {

  "part01" should "be correct" in {
    part01(input) shouldBe 69795
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 208437
  }

}
