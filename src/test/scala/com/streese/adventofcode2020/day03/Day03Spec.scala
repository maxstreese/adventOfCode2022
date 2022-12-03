package com.streese.adventofcode2020.day03

import com.streese.adventofcode2020.PuzzlesSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day03Spec extends PuzzlesSpec(3) {

  "part01" should "be correct" in {
    part01(lines) shouldBe 8053
  }

  "part02" should "be correct" in {
    part02(lines) shouldBe 2425
  }

}
