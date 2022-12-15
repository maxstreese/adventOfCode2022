package com.streese.aoc.day14

import com.streese.aoc.PuzzlesSpec

import scala.util.chaining._

class Day14Spec extends PuzzlesSpec(14) {

  "" should "" in {
    val x = parse(example).pipe(Tilemap(_))
    println(x.sandDropped)
  }

  "part01" should "be correct" in {
    // println(part01(input))
    // part01(input) shouldBe ???
  }

  "part02" should "be correct" in {
    // println(part02(input))
    // part02(input) shouldBe ???
  }

}
