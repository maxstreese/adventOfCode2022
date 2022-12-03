package com.streese.adventofcode2020

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

abstract class PuzzlesSpec(day: Int) extends AnyFlatSpec with Matchers {

  val lines: Seq[String] = os.read.lines(os.resource / f"day$day%02.0f" / "input.txt")

}
