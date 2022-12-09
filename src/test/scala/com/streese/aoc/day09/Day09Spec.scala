package com.streese.aoc.day09

import com.streese.aoc.PuzzlesSpec
import scala.collection.mutable

class Day09Spec extends PuzzlesSpec(9) {

  "part01" should "be correct" in {
    part01(lines) shouldBe 6181
  }

  "part02" should "be correct" in {
    part02(lines) shouldBe 2386
  }

}
