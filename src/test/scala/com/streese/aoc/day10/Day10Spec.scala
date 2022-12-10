package com.streese.aoc.day10

import com.streese.aoc.PuzzlesSpec

import scala.util.chaining._

class Day10Spec extends PuzzlesSpec(10) {

  "part01" should "be correct" in {
    part01(input) shouldBe 14220
  }

  "part02" should "be correct" in {
    val expected =
      """|████ ███   ██  ███  █    ████ ████ █  █ 
         |   █ █  █ █  █ █  █ █    █       █ █  █ 
         |  █  █  █ █  █ █  █ █    ███    █  █  █ 
         | █   ███  ████ ███  █    █     █   █  █ 
         |█    █ █  █  █ █ █  █    █    █    █  █ 
         |████ █  █ █  █ █  █ ████ █    ████  ██  """.stripMargin
    part02(input) shouldBe expected
  }

}
