package com.streese.aoc.day02

enum Hand:
  case Rock, Paper, Scissors

object Hand:
  def parse(s: Char): Hand = s match
    case r if (r == 'A' || r == 'X') => Hand.Rock
    case p if (p == 'B' || p == 'Y') => Hand.Paper
    case s if (s == 'C' || s == 'Z') => Hand.Scissors
end Hand

enum Result:
  case Draw, Win, Loss

object Result:
  def parse(s: Char): Result = s match
    case 'X' => Result.Loss
    case 'Y' => Result.Draw
    case 'Z' => Result.Win
end Result

def parseHandHand(lines: Seq[String]): Seq[(Hand, Hand)] =
  lines
    .map(_.toList)
    .flatMap {
      case theirs :: ' ' :: mine :: Nil => Some(Hand.parse(theirs), Hand.parse(mine))
      case _                            => None
    }

def score(theirs: Hand, mine: Hand): Int =
  val handScore = mine match
    case Hand.Rock     => 1
    case Hand.Paper    => 2
    case Hand.Scissors => 3
  handScore + judge(theirs, mine)

def judge(theirs: Hand, mine: Hand): Int =
  val loss = 0
  val draw = 3
  val win  = 6
  (theirs, mine) match
    case (Hand.Rock,     Hand.Rock    ) => draw
    case (Hand.Rock,     Hand.Paper   ) => win
    case (Hand.Rock,     Hand.Scissors) => loss
    case (Hand.Paper,    Hand.Rock    ) => loss
    case (Hand.Paper,    Hand.Paper   ) => draw
    case (Hand.Paper,    Hand.Scissors) => win
    case (Hand.Scissors, Hand.Rock    ) => win
    case (Hand.Scissors, Hand.Paper   ) => loss
    case (Hand.Scissors, Hand.Scissors) => draw

def part01(lines: Seq[String]): Int =
  parseHandHand(lines)
    .map(score)
    .reduce(_ + _)

def parseHandResult(lines: Seq[String]): Seq[(Hand, Result)] =
  lines
    .map(_.toList)
    .flatMap {
      case theirs :: ' ' :: result :: Nil => Some(Hand.parse(theirs), Result.parse(result))
      case _                              => None
    }

def determineHand(theirs: Hand, result: Result): Hand =
  (theirs, result) match
    case (Hand.Rock,     Result.Draw) => Hand.Rock
    case (Hand.Rock,     Result.Win ) => Hand.Paper
    case (Hand.Rock,     Result.Loss) => Hand.Scissors
    case (Hand.Paper,    Result.Draw) => Hand.Paper
    case (Hand.Paper,    Result.Win ) => Hand.Scissors
    case (Hand.Paper,    Result.Loss) => Hand.Rock
    case (Hand.Scissors, Result.Draw) => Hand.Scissors
    case (Hand.Scissors, Result.Win ) => Hand.Rock
    case (Hand.Scissors, Result.Loss) => Hand.Paper
  
def part02(lines: Seq[String]): Int =
  parseHandResult(lines)
    .map((theirs, result) => theirs -> determineHand(theirs, result))
    .map(score)
    .reduce(_ + _)
