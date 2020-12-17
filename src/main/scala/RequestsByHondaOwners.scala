package submissions

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

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

  //println(testArray)
  //testArray.foreach(println)

  var makersInSubmission = ArrayBuffer[String]()
  for (i <- lineToArrayBuffer) {
    val outOfBoundExceptionCheck = i.split(",", -1).length
    if (outOfBoundExceptionCheck > 1) {
      val maker = i.split(",", -1)(columnOfMake - 1).toUpperCase()
      if (!maker.isEmpty()) {
        makersInSubmission += maker
      }
    }
  }

  //Content Check if the output is really car make only
  //putting it in a map and counting how many honda's there
  val hondaCount = Map[String, Int]()
  for (x <- makersInSubmission) {
    if (x.split(" ", -1).length > 2) {} 
    else {
      if (hondaCount.contains(x)) {
        hondaCount(x) += 1
      }
      else{
          hondaCount(x) = 1
      }
    }
  }
  hondaCount.foreach(println)

  //show which car make has the most estimate requests - descending order
  //type in honda and the number of submissions and percentage
  //put all different methods into separate classes
  //convert back to csv file
  //write readme, comments, and scala docs


  println()
}
