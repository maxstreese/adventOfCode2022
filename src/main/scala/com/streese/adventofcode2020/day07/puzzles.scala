package com.streese.adventofcode2020.day07

import scala.collection.mutable

sealed trait Move
case class   Down(dir: String) extends Move
case object  Up                extends Move

sealed trait FileSystemObject
case class   Directory(name: String, contents: Seq[FileSystemObject]) extends FileSystemObject
case class   File(name: String, size: Int)                            extends FileSystemObject

object Directory:
  def apply(name: String): Directory = Directory(name, Seq.empty)

sealed trait Command
case class   ChangeDirectory(move: Move)                  extends Command
case class   ListContents(output: List[FileSystemObject]) extends Command

def parse(lines: Seq[String]): Seq[Command] =
  val commands = mutable.ListBuffer.empty[Command]
  val contents = mutable.ListBuffer.empty[FileSystemObject]
  var inLs = false
  for line <- lines.tail do
    if inLs && ! line.startsWith("$ ") then
      val fso =
        if line.startsWith("dir ") then Directory(line.stripPrefix("dir "))
        else File(line.split(" ")(1), line.takeWhile(_ != ' ').toInt)
      contents.append(fso)
    if line.startsWith("$ ") then
      if inLs then
        commands.append(ListContents(contents.toList))
        contents.clear()
        inLs = false
    if line.startsWith("$ cd") then
      val movement = if line.endsWith("..") then Up else Down(line.stripPrefix("$ cd "))
      commands.append(ChangeDirectory(movement))
    if line == "$ ls" then
      inLs = true
  commands.toList

def part01(lines: Seq[String]): Unit = ???

def part02(lines: Seq[String]): Unit = ???
