package com.streese.aoc.day07

import scala.collection.mutable
import scala.util.chaining._

sealed trait Move
case class   Down(name: String) extends Move
case object  Up                 extends Move

sealed trait FileSystemObject
case class   DirectoryID(name: String)        extends FileSystemObject
case class   FileID(name: String, size: Long) extends FileSystemObject

sealed trait Command
case class   ChangeDirectory(move: Move)                  extends Command
case class   ListContents(output: List[FileSystemObject]) extends Command

def parse(lines: Seq[String]): List[Command] =
  val commands = mutable.ListBuffer.empty[Command]
  val contents = mutable.ListBuffer.empty[FileSystemObject]
  var inLs = false
  for line <- lines.tail do
    if inLs && ! line.startsWith("$ ") then
      val id =
        if line.startsWith("dir ") then DirectoryID(line.stripPrefix("dir "))
        else FileID(line.split(" ")(1), line.takeWhile(_ != ' ').toLong)
      contents.append(id)
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
  if inLs then
    commands.append(ListContents(contents.toList))
    contents.clear()
  commands.toList

extension (s: String)
  def stripPathEnd: String =
    val parts = s.split("/")
    parts.take(parts.size - 1).mkString("/")
  def appendPathEnd(e: String): String = s ++ "/" ++ e

def buildFileMap(commands: List[Command]): Map[String, Long] =
  val out = mutable.Map.empty[String, Long]
  def recur(path: String, cmds: List[Command]): Unit =
    cmds match
      case head :: tail =>
        head match
          case ChangeDirectory(move) =>
            move match
              case Down(name) =>
                recur(path.appendPathEnd(name), tail)
              case Up =>
                recur(path.stripPathEnd, tail)
          case ListContents(output) =>
            output.foreach {
              case FileID(name, size) => out.update(path.appendPathEnd(name), size)
              case _ => ()
            }
            recur(path, tail)
      case _ => ()
  recur("", commands)
  out.toMap

def buildDirMap(files: Map[String, Long]): Map[String, Long] =
  val out = mutable.Map.empty[String, Long]
  files.foreach { (p, s) =>
    var pStripped = p
    while pStripped != "" do
      out.updateWith(pStripped.stripPathEnd)(_.map(_ + s).orElse(Some(s)))
      pStripped = pStripped.stripPathEnd
  }
  out.get("").foreach { s =>
    out.remove("")
    out.put("/", s)
  }
  out.toMap

def part01(lines: Seq[String]): Long =
  parse(lines)
    .pipe(buildFileMap)
    .pipe(buildDirMap)
    .values
    .filter(_ <= 100000)
    .sum

def part02(lines: Seq[String]): Long =
  val dirs = parse(lines).pipe(buildFileMap).pipe(buildDirMap)
  val fileSystemSize   = 70000000L
  val requiredFreeSize = 30000000L
  val unusedSize       = fileSystemSize - dirs("/")
  val toFreeSize       = requiredFreeSize - unusedSize
  dirs.values.filter(_ >= toFreeSize).min
