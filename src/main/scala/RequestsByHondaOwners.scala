package submissions

import scala.collection.mutable.ArrayBuffer

object RequestsByHondaOnwers extends App{
    println()
    val file = io.Source.fromFile("ERS.csv")
    var lineToArrayBuffer = ArrayBuffer[String]()
    for(lines <- file.getLines()){
        lineToArrayBuffer += lines
    }
    
    val numberOfSubmissions = lineToArrayBuffer.length-1
    println(s"Total # of estimate requests submitted: $numberOfSubmissions\n")

    println("The first row of the file contains a label of each column.")
    println("Printing out the first line: ")
    val firstLine = lineToArrayBuffer.apply(0).toUpperCase().split(",")
    var firstLineSplit = firstLine.mkString(" | ")
    println(firstLineSplit)

    var column = 1
    for(i <- 0 to firstLine.length-1) {
        if(firstLine(i).equals("MAKE")) column += i
    }
    column match{
        case 1 => print(s"Car Make is in the $column" +"st column. ")
        case 2 => print(s"Car Make is in the $column" +"nd column. ")
        case 3 => print(s"Car Make is in the $column" +"rd column. ")
        case _ => print(s"Car Make is in the $column" +"th column. ")
    }
    println("Pulling the data...")

    val carMakeData = ArrayBuffer[String]()
    for(i <- lineToArrayBuffer){
    // var newArray = i.split(",")
    // println(newArray(1))
    // null handling
    }

    println()
}