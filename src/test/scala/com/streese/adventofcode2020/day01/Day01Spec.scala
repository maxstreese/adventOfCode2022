package com.streese.adventofcode2020.day01

import com.streese.adventofcode2020.PuzzlesSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day01Spec extends PuzzlesSpec(1) {

  "part01" should "be correct" in {
    part01(lines) shouldBe 69795
  }

  "part02" should "be correct" in {
    part02(lines) shouldBe 208437
  }

}
