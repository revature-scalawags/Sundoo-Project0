package submissions

import scala.collection.mutable.ArrayBuffer

object RequestsByHondaOnwers extends App {
  println()
  val file = io.Source.fromFile("ERS.csv")
  var lineToArrayBuffer = ArrayBuffer[String]()
  for (lines <- file.getLines()) {
    lineToArrayBuffer += lines
  }

  val numberOfSubmissions = lineToArrayBuffer.length - 1
  println(s"Total # of estimate requests submitted: $numberOfSubmissions\n")

  println("The first row of the file contains a label of each column.")
  println("Printing out the first line: ")
  val firstLine = lineToArrayBuffer.apply(0).toUpperCase().split(",")
  var firstLineSplit = firstLine.mkString(" | ")
  println(firstLineSplit)

  var columnOfMake = 0
  for (i <- 0 to firstLine.length - 1) {
    if (firstLine(i).equals("MAKE")) columnOfMake = i + 1
  }
  columnOfMake match {
    case 1 => print(s"Car Make is in the $columnOfMake" + "st column. ")
    case 2 => print(s"Car Make is in the $columnOfMake" + "nd column. ")
    case 3 => print(s"Car Make is in the $columnOfMake" + "rd column. ")
    case _ => print(s"Car Make is in the $columnOfMake" + "th column. ")
  }
  println("Pulling the data...")

  for (i <- 1 to 9) {
    val test = lineToArrayBuffer.apply(i)
    val testArray = test.split(",", -1)
    if (testArray.length <= 1) {} else {
      val testArrayColumn2 = test.split(",", -1)(1)
      if (testArrayColumn2.isEmpty()) {
        println("it's empty")
      } else {
        println("it's not empty")
      }
    }
  }

  //println(testArray)
  //testArray.foreach(println)

  var MakersInSubmission = ArrayBuffer[String]()
  for (i <- lineToArrayBuffer) {
    val OutOfBoundExceptionCheck = i.split(",", -1).length
    if (OutOfBoundExceptionCheck > 1) {
      val maker = i.split(",", -1)(columnOfMake - 1)
      if (!maker.isEmpty()) {
          MakersInSubmission += maker
      }
    }
  }

  MakersInSubmission.foreach(println)

  println()
}
