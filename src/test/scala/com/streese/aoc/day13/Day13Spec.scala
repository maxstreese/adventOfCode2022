package com.streese.aoc.day13

import com.streese.aoc.PuzzlesSpec
import scala.util.chaining._

class Day13Spec extends PuzzlesSpec(13) {

  "something" should "work" in {
    pprint.pprintln(Token.parse("[[[]]]").pipe(Packet.parse))
    // example
    //   .filterNot(_.isBlank)
    //   .map(_.toList.toSeq)
    //   .take(12)
    //   .map(Token.parse)
    //   .map(Packet.parse)
    //   .foreach(pprint.pprintln(_))
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
