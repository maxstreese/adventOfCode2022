package com.streese.aoc.day02

import com.streese.aoc.PuzzlesSpec
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day02Spec extends PuzzlesSpec(2) {

  "part01" should "be correct" in {
    part01(input) shouldBe 14264
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 12382
  }

}
