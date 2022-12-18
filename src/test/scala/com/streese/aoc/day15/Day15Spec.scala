package com.streese.aoc.day15

import com.streese.aoc.PuzzlesSpec

import scala.util.chaining._

class Day15Spec extends PuzzlesSpec(15) {


  "part01" should "be correct" in {
    part01(input) shouldBe 5508234
  }

  "part02" should "be correct" in {
    part02(input) shouldBe 10457634860779L
  }

}
