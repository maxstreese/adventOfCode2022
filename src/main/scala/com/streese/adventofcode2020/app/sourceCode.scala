package com.streese.adventofcode2020.app

def implementationCode(day: Int): String =
  f"""|package com.streese.adventofcode2020.day$day%02d
      |
      |def part01(lines: Seq[String]): Unit = ???
      |
      |def part02(lines: Seq[String]): Unit = ???
      |""".stripMargin

def testCode(day: Int) =
  f"""|package com.streese.adventofcode2020.day$day%02d
      |
      |import com.streese.adventofcode2020.PuzzlesSpec
      |
      |class Day${day}%02dSpec extends PuzzlesSpec($day) {
      |
      |  "part01" should "be correct" in {
      |    // println(part01(lines))
      |    // part01(lines) shouldBe ???
      |  }
      |
      |  "part02" should "be correct" in {
      |    // println(part02(lines))
      |    // part02(lines) shouldBe ???
      |  }
      |
      |}
      |""".stripMargin