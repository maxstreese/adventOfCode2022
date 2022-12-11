package com.streese.aoc.day11

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day11Spec extends PuzzlesSpec(11) {

  "part01" should "be correct" in {
    part01(input) shouldBe 50616L
  }

  "part02" should "be correct" in {
    println(part02(example))
    // part02(input) shouldBe ???
  }

}
