package com.streese.aoc.day13

import com.streese.aoc.PuzzlesSpec

class Day13Spec extends PuzzlesSpec(13) {

  "something" should "work" in {
    // val tokens = Token.parse("[1,1,3,1,1]")
    val tokens = Token.parse("[[1],[2,3,4]]")
    val packet = Packet.parse(tokens)
    pprint.pprintln(packet)
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
