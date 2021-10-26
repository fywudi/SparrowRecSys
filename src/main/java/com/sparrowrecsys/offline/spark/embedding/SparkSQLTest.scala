package com.sparrowrecsys.offline.spark.embedding

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.ml.feature.OneHotEncoderEstimator
import org.apache.spark.sql.SparkSession

object SparkSQLTest{
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "c:\\winutil\\")

    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("ctrModel")
      .set("spark.submit.deployMode", "client")

    val spark = SparkSession.builder.config(conf).getOrCreate()
    import spark.implicits._

    val rawSampleDataPath = "/webroot/sampledata/ratings.csv"
    //path of rating data
    val ratingsResourcesPath = this.getClass.getResource(rawSampleDataPath)
    val ratingSamples = spark.read.format("csv").option("header", "true").load(ratingsResourcesPath.getPath)
    ratingSamples.show(3)
    ratingSamples.printSchema()
    ratingSamples.select("userId").show(3)
//    ratingSamples.select("userId", "movieId", "timestamp").where($"rating" > 4.5).show(3)
    ratingSamples.groupBy("rating").count().show()
    ratingSamples.createOrReplaceTempView("samples")
    val sqlDF = spark.sql("SELECT userId, movieId, timestamp FROM samples")
    sqlDF.show(3)
  }
}
