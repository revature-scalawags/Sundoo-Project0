package submissions

import scala.sys.process._
import scala.language.postfixOps

object test extends App{

  var fileListArray = ("ls"!!).split("\\s|\\\\n", -1)


  fileListArray.foreach(println)
  println(fileListArray.length)

  println(fileListArray.contains("ERS.csv"))

}