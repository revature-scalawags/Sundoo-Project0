import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.{ArrayBuffer, ListBuffer, Map}
import scala.collection.immutable.ListMap
import scala.util.Random
import submissions._

class EstimateRequestsAnalyzerTest extends AnyFunSuite{
    val r = Random
    val testString = "testString"

    ignore("EstimateRequestAnalyzer should Call informationBySchema method and give Boolean value") {
        assert(EstimateRequestAnalyzer.estimateRequestAnalyzer == true)
    }

    ignore("analyze what's in lines and columns and give Boolean value") {
        val testArrayBuffer = ArrayBuffer[String]("ab", "cd", "ef")
        assert(EstimateRequestAnalyzer.informationBySchema(r.nextInt(), testArrayBuffer) == true)
    }

    test("check if an input is a Character value only") {
        assert(EstimateRequestAnalyzer.isLetter(testString.charAt(r.nextInt(testString.length))) == true)
    }
    
    test("give the percentage value of a specific car make's requests out of total requests") {
        assert(EstimateRequestAnalyzer.percentageCalculator(10, 100, testString) == 10.00)
    }

    ignore("save the information of a selected schema's list and the requests submitted into .csv file, then give Boolean value"){
        val testListBuffer1 = ListBuffer[String]("ab", "cd", "ef")
        val testListBuffer2 = ListBuffer[String]("gh", "ij", "kl")
        assert(EstimateRequestAnalyzer.saveToCSV(testListBuffer1, testListBuffer2) == true)
    }  

    ignore("save the information of a specific car make's requests and its percentage into .csv file, then give Boolean value") {
        assert(EstimateRequestAnalyzer.saveToCSV(r.nextInt, r.nextInt, testString) == true)
    }

    ignore("prompt a user, only read the user's String input, and return it in a String format") {
        assert(EstimateRequestAnalyzer.inputStringReader() == testString)
    }

    ignore("prompt a user, only read the user's Integer input, and return it in an Integer format") {
        assert(EstimateRequestAnalyzer.inputReader() == r.nextInt)
    }

    test("sort a map in three ordering options and return a sorted ListMap") {
        val testMap = Map[String, Int]("a" -> 1, "b" -> 2, "c" -> 3)
        val testResultListMap = ListMap[String, Int]("c" -> 3, "b" -> 2, "a" -> 1)
        assert(EstimateRequestAnalyzer.schemaSort(3, testMap) == testResultListMap)
    }
}