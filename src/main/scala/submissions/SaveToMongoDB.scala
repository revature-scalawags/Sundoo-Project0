package submissions

import tour.Helpers._
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromRegistries, fromProviders}
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._




object SaveToMongoDB{
  case class PercentageData(carMake: String, estimateRequests: String, totalEstiamteRequests: String, percentage: String)
  val providers = fromProviders(classOf[PercentageData])
  val registry = fromRegistries(providers, DEFAULT_CODEC_REGISTRY)
  val codeRegistry = fromRegistries(fromProviders(classOf[PercentageData]), DEFAULT_CODEC_REGISTRY)
  val mongoClient = MongoClient()
  val database = mongoClient.getDatabase("myDBs").withCodecRegistry(codeRegistry)




  def getPercentage(string: String, num1: String, 
  num2: String, percentage: String): PercentageData = {
    val percentageVal: PercentageData = PercentageData(string, num1, 
  num2, percentage)
  percentageVal
  }

  def insertPercentage(data: PercentageData): Unit = {
      val collection: MongoCollection[PercentageData] = database.getCollection("myCollection")
      collection.insertOne(data).results()
    }
} 