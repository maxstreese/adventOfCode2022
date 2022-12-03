package com.streese.adventofcode2020.day02

import com.streese.adventofcode2020.PuzzlesSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day02Spec extends PuzzlesSpec(2) {

  "part01" should "be correct" in {
    part01(lines) shouldBe 14264
  }

  "part02" should "be correct" in {
    part02(lines) shouldBe 12382
  }

}
