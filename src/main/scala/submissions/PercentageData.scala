package submissions

import org.mongodb.scala.bson.ObjectId

object PercentageData{
    def apply(carMake: String, num1: String, num2: String, percentage: String): PercentageData = 
    PercentageData(carMake, num1, num2, percentage)
}
case class PercentageData(carMake: String, estimateRequests: String, totalEstiamteRequests: String, percentage: String)