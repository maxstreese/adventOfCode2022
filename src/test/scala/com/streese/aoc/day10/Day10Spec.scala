package com.streese.aoc.day10

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day10Spec extends PuzzlesSpec(10) {

  "part01" should "be correct" in {
    part01(input) shouldBe 14220
  }

  "part02" should "be correct" in {
    val res = part02(example).dropRight(1)
    println(input.map(Instruction.parse).pipe(xRegHist).size)
    // part02(input) shouldBe ???
  }

}
