package com.streese.adventofcode2020.day07

import com.streese.adventofcode2020.PuzzlesSpec

class Day07Spec extends PuzzlesSpec(7) {

  "parse" should "be correct" in {
    val expected = Seq(
      ListContents(List(Directory("a"), File("b.txt",14848514), File("c.dat",8504156), Directory("d"))),
      ChangeDirectory(Down("a")),
      ListContents(List(Directory("e"), File("f",29116), File("g",2557), File("h.lst",62596))),
      ChangeDirectory(Down("e")),
      ListContents(List(File("i",584))),
      ChangeDirectory(Up),
      ChangeDirectory(Up),
      ChangeDirectory(Down("d")),
    )
    parse(example) shouldBe expected
  }

  "part01" should "be correct" in {
    // println(part01(lines))
    // part01(lines) shouldBe ???
  }

  "part02" should "be correct" in {
    // println(part02(lines))
    // part02(lines) shouldBe ???
  }

}
