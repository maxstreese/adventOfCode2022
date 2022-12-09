# Advent Of Code 2022 - My Solutions

My solutions for the Advent of Code 2022.

## Things Of Note

### Leaderboard Participation

I do not participate in this for the leaderboard. I am not into competitive programming and the release of the puzzles at 6 am in my local time does not help things either.

### Code Quality

That being said, please do not judge my professional coding style by the code in this repository. I do not put emphasize on readability, much less maintainability here, I just want to solve the puzzles and see how far I get.

The same can be said about the efficiency of the solutions.

### Language And Development Environment

As I am writing this, I am using Scala 3 as my language, SBT as my build tool and VS Code as my IDE. So far (day 3) I have been sticking to a functional style to solve the puzzles. This is most notable by the abscence of loops.

I would be curious to solve the puzzles with either C or C++ in the future to get better at solving coding challenges in a more procedural style, closer to the machine and with more emphasis on performance. I have to admit to myself that I have become quite reliant on Scala's high level of abstraction and the power of it's standard library (most of all the collections).

### Structure Of The Repository

I follow the standard convention for JVM languages w.r.t. folder and package naming. You will find the implementation of the solutions in `src/main/scala/com/streese/aoc/dayXX` folders. I test/run the code via tests in `src/test/scala/com/streese/aoc/dayXX` folders.

## Setting Up A Day

The repository contains a small app one can use to set up a given day (the below assumes you have [Bloop](https://scalacenter.github.io/bloop/) installed): 

```
> bloop run aoc -- -h
Set up any given day and optionally download its input. Existing files are kept.
Usage:  [options]

  -h, --help
  -d, --day <d>           the day to set up
  -o, --overwrite         overwrite any existing files
  -s, --session-file <p>  path to the session cookie file [default: ~/.adventofcode.session]
```

The setup code is heavily inspired by [aoc-cli](https://github.com/scarvalhojr/aoc-cli). I just wanted to roll my own in order to customize things a little and also have things more self-contained.
