package submissions

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import io.Source._
import scala.collection.immutable.ListMap
import java.io.{BufferedWriter, FileWriter}
import scala.jdk.CollectionConverters._
import au.com.bytecode.opencsv.CSVWriter

object RequestsByHondaOnwers extends App {
  println()

  println("Starting the program...\n")
  println("Showing a list of files in your folder: ")
  //show a list of files 

  print("Which file you want to import? Please type in a number: ")

  println("#3(ERS.csv) selected")
  println()
  println("Importing and opening the file...")
  println()

  //Fetching  Estimate Request Submission.csv file into my scala program
  //Then, dividing the file by lines
  val file = fromFile("ERS.csv")
  var lineToArrayBuffer = ArrayBuffer[String]()
  for (lines <- file.getLines()) {
    lineToArrayBuffer += lines
  }

  println("ERS.csv imported successfully")
  println()

  // val numberOfSubmissions = lineToArrayBuffer.length - 1
  // println(s"Total # of estimate requests submitted: $numberOfSubmissions\n")

  println("Below are the shcemas/headings of the file: ")
  val firstLine = lineToArrayBuffer.apply(0).toUpperCase().split(",")

  //Printing out optings to choose from
  for (i <- 0 to firstLine.length - 1) {
    println(s"${i + 1}. ${firstLine(i)}")
  }
  
  println("Type in a number for information you want to access: ")
  println()
  println("#2(Make) selected")
  println()

  //Selecting a column depending on the number chosen by a customer
  var columnSelected = 0
  for (i <- 0 to firstLine.length - 1) {
    if (firstLine(i).equals("MAKE")) columnSelected = i + 1
  }
  println("Fetching the information you requested...")
  println()

  println("How would you like to see the information\n" +
    "1. Alphabetical Order\n" +
    "2. Ascending Order (Least # of submissions -> Most # of submissions)\n" +
    "3. Descending Order (Most # of submissions -> Least # of submissions)\n" +
    "Type in a number: ")
  
  println()
  println("#3(Descending Order) selected")
  println()

  //println(testArray)
  //testArray.foreach(println)
 

  //Storing information fetched from the selected column into an ArrayBuffer
  var columnSelectedToArray = ArrayBuffer[String]()
  for (i <- lineToArrayBuffer) {
    val outOfBoundExceptionCheck = i.split(",", -1).length
    if (outOfBoundExceptionCheck > 1) {
      val column = i.split(",", -1)(columnSelected - 1).toUpperCase()
      if (!column.isEmpty()) {
        columnSelectedToArray += column
      }
    }
  }

  //Content Check if the output is really car make only
  //putting it in a map and counting how many honda's there
  val contentToMap = Map[String, Int]()
  for (x <- columnSelectedToArray) {
    val contentFilter = x.split("-|\\s", -1).length
    val contentCheck =  x.split("-|\\s", -1).mkString(" ")
    if (contentFilter < 3) {
      if (contentToMap.contains(contentCheck)) {
        contentToMap(contentCheck) += 1
      }else{ 
      contentToMap(contentCheck) = 1
      }
    }
  }

  //Sorting the Map in different options
  var optionSelected = 3
  val sortedMap: ListMap[String, Int] = optionSelected match{
    case 1 => ListMap(contentToMap.toSeq.sortWith(_._1 < _._1): _*)
    case 2 => ListMap(contentToMap.toSeq.sortWith(_._2 < _._2): _*)
    case 3 => ListMap(contentToMap.toSeq.sortWith(_._2 > _._2): _*)
    case _ => ListMap()
  }

  //Printing out the Map in a selected order
  //Calculating the number of total requests submitted
  var numOfTotalRequests = 0
  sortedMap.keys.foreach{x =>
    println(f"${x}%-13s - ${sortedMap(x)}%2d")
    numOfTotalRequests += sortedMap(x)
  }
  println()
  println("If you want to know the percentage of a specific car make's reqeusts out of the total requests, \n" +
    "type in the name of a car make: \n" +
    "(Type 'Quit' to end the program here.)")
  println()
  

  val carMakeTyped = "Honda".toUpperCase()
  println(s"$carMakeTyped is selected. Calculating...")
  println()

val percentage = ((sortedMap(carMakeTyped)).toFloat / (numOfTotalRequests).toFloat)*100
 if(sortedMap.contains(carMakeTyped)){
        println("|    Car Make    "+"|  Estimate Reqeusts submitted  "+"|  Percentage calculated  |\n" +
               s"|__________________________________________________________________________|\n"+
               s"|                                                                          |\n"+
               f"|     ${carMakeTyped}%-11s" + 
               f"|         ${sortedMap(carMakeTyped)}%d"+ " out of " +f"${numOfTotalRequests}%-12d"+
               f"|       ${percentage}%2.2f" + "%            |"
        )
  }else{
        println(s"There is no information about $carMakeTyped.")
  }
  
  println()

  println("Before you quit, do you want to save this information into .csv file?\n"+
  "Hit 'Y' to save or 'N' to exit"
  )

 println("Saving it into .csv file...")
 val outputFile = new BufferedWriter(new FileWriter("C:/Users/spark/Project00/project0/src/main/scala/output.csv"))
 val csvWriter = new CSVWriter(outputFile)
 val fileSchema = Array("Car Make", "Estimate Requests by the Make", "Total Estimate Requests", "Percentage calculated")
 var values = Array(
   carMakeTyped, sortedMap(carMakeTyped).toString(), 
   numOfTotalRequests.toString(), s"${percentage - (percentage%0.01)}%") //showing only 2 decimal places

 var listOfValues = List(fileSchema, values)
 csvWriter.writeAll(listOfValues.asJava)
 outputFile.close()
  //put all different methods into separate classes
  //write readme, comments, and scala docs

  println()
}
