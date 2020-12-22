package submissions

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import io.Source._
import scala.collection.immutable.ListMap
import java.io.{BufferedWriter, FileWriter}
import scala.jdk.CollectionConverters._
import au.com.bytecode.opencsv.CSVWriter
import java.util.Scanner
import scala.sys.process._
import scala.language.postfixOps
import com.typesafe.scalalogging.LazyLogging

object EstimateRequestAnalyzer extends LazyLogging {

  logger.warn("Starting the Application")
  logger.error("Starting the Application")

  def main(args: Array[String]): Unit = {
    logger.warn("Executing the Main method")
    logger.error("Executing the Main method")

    //Check Args and run, could possibly add menu option here
    if(args.length >= 0){
      estimateRequestAnalyzer()
    }
  }

  def estimateRequestAnalyzer(): Unit = {
    logger.warn("Calling the estimateRequestAnalyzer method")
    logger.error("Calling the estimateRequestAnalyzer method")
    println("\n\n")

    println("Starting the program...\n")
    print("Showing the list of files in your folder -->   ")

    //Showing the list of files in the folder
    var keyValuePair = Map[Int, String]()
    var fileListArray = ("ls" !!).split("\\s")
    fileListArray.foreach(x => print(s"| $x "))

    fileListArray = ("ls" !!).split("\\s|\\\\n", -1).map(_.toUpperCase())
    println()
    println()

    print(
      "Which file you want to import? Please type in the name of the file: "
    )
    var fileTyped = inputStringReader()
    println("Importing and opening the file...")

    println()
    //wait 5seconds to load

    //Fetching the selected file into my scala application
    while (
      //Catching any exception or errors
      fileTyped.isEmpty() || !fileListArray.contains(fileTyped.toUpperCase())
    ) {
      println("No such file is found. Please type in the name of the file: ")
      fileTyped = inputStringReader()
    }
    val file = fromFile(fileTyped)
    var lineToArrayBuffer = ArrayBuffer[String]()
    //Parsing the file into lines
    for (lines <- file.getLines()) {
      lineToArrayBuffer += lines
    }

    println(s"${fileTyped.toUpperCase} imported successfully")
    println()

    println("Below are the shcemas/headings of the file: ")
    val firstLine = lineToArrayBuffer.apply(0).toUpperCase().split(",")

    //Printing out optings to choose from
    for (i <- 0 to firstLine.length - 1) {
      println(s"${i + 1}. ${firstLine(i)}")
    }

    //Accessing information by the number selected
    print("Type in a number for information you want to access: ")
    var schemaSelected: Int = inputReader()

    println()
    schemaSelected match {
      case 1 => informationBySchema(1)
      case 2 => informationBySchema(2)
      case 3 => informationBySchema(3)
      case 4 => informationBySchema(4)
      case 5 => informationBySchema(5)
      case 6 => informationBySchema(6)
      case _ => informationBySchema(0)
    }

    def informationBySchema(schema: Int): Unit = {
      //Catching any exception or errors
      if (schema < 1 || schema > 6) {
        print("No such schema is found. Type in a number again: ")
        informationBySchema(inputReader())
      } else {
        println("Fetching the information you requested...")
        println()

        //Storing the information fetched from the selected column into an ArrayBuffer
        var columnSelectedToArray = ArrayBuffer[String]()
        for (i <- lineToArrayBuffer) {
          val outOfBoundExceptionCheck = i.split(",", -1).length
          if (outOfBoundExceptionCheck > 1) { //Making sure each line contains at least two columns - Name of the info & # of requests submitted
            val column = i.split(",", -1)(schema - 1).toUpperCase()
            if (!column.isEmpty()) {
              columnSelectedToArray += column
            }
          }
        }

        //Content Check to make sure the stored info matches with info users want
        //putting it in a map and counting the # of requests submitted
        val contentToMap = Map[String, Int]()
        for (x <- columnSelectedToArray) {
          val contentFilter = x.split("-|\\s", -1).mkString(" ")
          val filteredContentSize = x.split("-|\\s", -1).length
          if (filteredContentSize < 3) {
            if (contentToMap.contains(contentFilter)) {
              contentToMap(contentFilter) += 1
            } else {
              contentToMap(contentFilter) = 1
            }
          }
        }

        println(
          "How would you like to see the information?\n" +
            "1. Alphabetical Order\n" +
            "2. Ascending Order (Least # of submissions -> Most # of submissions)\n" +
            "3. Descending Order (Most # of submissions -> Least # of submissions)\n" +
            "Type in a number: "
        )

        //Sorting the Map to display it in three different ordering options
        var orderSelected = inputReader()
        while (orderSelected < 0 || orderSelected > 3) {
          print("Invalid Input. Please re-type in a number: ")
          orderSelected = inputReader()
        }
        val sortedSchema = schemaSort(orderSelected, contentToMap)

        //Printing out the Map in a selected order
        //Calculating the number of total requests submitted
        var numOfTotalRequests = contentToMap.foldLeft(0)(_ + _._2)
        var carMakesList = ListBuffer[String]()
        var requestsList = ListBuffer[String]()
        sortedSchema.keys.foreach { x =>
          println(f"${x}%-13s - ${sortedSchema(x)}%2d")
          carMakesList += x
          requestsList += sortedSchema(x).toString()
        }
        println()
        println(
          "If you want to know the percentage of a specific car make's reqeusts out of the total requests, \n" +
            "type in the name of a car make: \n" +
            "(Type 'Quit' to quit the application here.)"
        )

        //Making sure that the user input is String value only
        def isLetter(c: Char) = c.isLetter && c <= 'z'
        var carMakeTyped = inputStringReader()
        var isLetterCheck: Boolean = true
        carMakeTyped.foreach(x =>
          if (!isLetter(x) || x.toString.isEmpty) {
            isLetterCheck = false
          }
        )

        //Catching any exception or errors
        while (!isLetterCheck) {
          print("Invalid Input. Please type in a letter format: ")
          carMakeTyped = inputStringReader()
          carMakeTyped.foreach(x =>
            if (!isLetter(x) | x.toString.isEmpty) {
              isLetterCheck = false
            } else {
              isLetterCheck = true
            }
          )
        }
        if (carMakeTyped.toUpperCase().contains("QUIT")) {
          saveToCSV(
            carMakesList,
            requestsList
          )
        } else {
          println(s"$carMakeTyped is selected. Calculating...")
          println()
          while (!sortedSchema.contains(carMakeTyped)) {
            println(s"There is no information about $carMakeTyped.")
            print("Please re-type in the name of a car make: ")
            carMakeTyped = inputStringReader
            println()
          }
          percentageCalculator(
            (sortedSchema(carMakeTyped)),
            numOfTotalRequests,
            carMakeTyped
          )
          println()
          println("Program completed successfully\n")
          saveToCSV(
            (sortedSchema(carMakeTyped)),
            numOfTotalRequests,
            carMakeTyped
          )
        }
      }
    }
  }
  def percentageCalculator(num1: Int, num2: Int, string: String): Double = {

    val percentage = (num1.toFloat / num2.toFloat) * 100
    println(
      "|    Car Make    " + "|  Estimate Reqeusts submitted  " + "|  Percentage calculated  |\n" +
        s"|__________________________________________________________________________|\n" +
        s"|                                                                          |\n" +
        f"|     ${string}%-11s" +
        f"|         ${num1}%d" + " out of " + f"${num2}%-12d" +
        f"|       ${percentage}%2.2f" + "%            |\n"
    )
    return percentage
  }

  def saveToCSV(list1: ListBuffer[String], list2: ListBuffer[String]): Unit = {

    println(
      "Before you quit, do you want to save this information into .csv file?\n" +
        "Hit 'Y' to save or 'N' to exit: "
    )
    var response = inputStringReader()
    if (response.contains("Y")) {

      println("Saving it into .csv file...")
      val outputFile = new BufferedWriter(
        new FileWriter(
          "./output.csv"
        )
      )

      val csvWriter = new CSVWriter(outputFile)
      val fileSchema = List("Car Make", "Estimate Requests by the Make")
      val combinedLists = list1.zip(list2)

      val rows = combinedLists
        .foldLeft(List[List[String]]()) { case (acc, (a, b)) =>
          List(a, b) +: acc
        }
        .reverse

      csvWriter.writeAll((fileSchema +: rows).map(_.toArray).asJava)
      outputFile.close()
    }
  }

  def saveToCSV(num1: Int, num2: Int, string: String): Unit = {

    println(
      "Before you quit, do you want to save this information into .csv file?\n" +
        "Hit 'Y' to save or 'N' to exit: "
    )
    var response = inputStringReader()
    while (response.isEmpty) {
      print("No value entered. Please re-type in your command: ")
      response = inputStringReader()
      println()
    }
    if (response.contains("Y")) {
      var percentage = (num1.toFloat / num2.toFloat) * 100
      println("Saving it into .csv file...")
      val outputFile = new BufferedWriter(
        new FileWriter(
          "./output.csv"
        )
      )
      val csvWriter = new CSVWriter(outputFile)
      val fileSchema = Array(
        "Car Make",
        "Estimate Requests by the Make",
        "Total Estimate Requests",
        "Percentage calculated"
      )
      var values = Array(
        string,
        num1.toString(),
        num2.toString(),
        s"${percentage - (percentage % 0.01)}%" //showing only 2 decimal places
      )

      var listOfValues = List(fileSchema, values)
      csvWriter.writeAll(listOfValues.asJava)
      outputFile.close()
    }
  }

  def inputStringReader(): String = {

    var scanner = new Scanner(System.in)
    var inputTyped = ""
    inputTyped = scanner.nextLine().toUpperCase()
    return inputTyped
  }

  def inputReader(): Int = {
    var scanner = new Scanner(System.in)
    var numSelected = 0
    var quit = false
    while (!quit) {
      if (scanner.hasNextInt()) {
        numSelected = scanner.nextInt()
        scanner.nextLine()
        println(s"#${numSelected} is selected.")

        quit = true
      } else {
        println()
        print("Invalid Input. Please type in a number: ")
        scanner.next()
      }
    }
    return numSelected
  }

  def schemaSort(
      orderSelected: Int,
      contentToMap: Map[String, Int]
  ): ListMap[String, Int] = {

    val sortedMap: ListMap[String, Int] = orderSelected match {
      case 1 => ListMap(contentToMap.toSeq.sortWith(_._1 < _._1): _*)
      case 2 => ListMap(contentToMap.toSeq.sortWith(_._2 < _._2): _*)
      case 3 => ListMap(contentToMap.toSeq.sortWith(_._2 > _._2): _*)
    }
    return sortedMap
  }
}
