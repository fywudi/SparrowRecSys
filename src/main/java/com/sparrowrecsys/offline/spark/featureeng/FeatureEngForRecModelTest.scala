package com.sparrowrecsys.offline.spark.featureeng

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{UserDefinedFunction, Window}
import org.apache.spark.sql.functions.{format_number, _}
import org.apache.spark.sql.types.{DecimalType, FloatType, IntegerType, LongType}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import redis.clients.jedis.Jedis
import redis.clients.jedis.params.SetParams

import scala.collection.immutable.ListMap
import scala.collection.{JavaConversions, mutable}

object FeatureEngForRecModelTest {
  def addSampleLabel(ratingSamples:DataFrame): DataFrame ={
//    ratingSamples.show(10, truncate = false)
//    ratingSamples.printSchema()
    val sampleCount = ratingSamples.count()
    ratingSamples.groupBy(col("rating")).count().orderBy(col("rating"))
      .withColumn("percentage", col("count")/sampleCount).show(100,truncate = false)

    ratingSamples.withColumn("label", when(col("rating") >= 3.5, 1).otherwise(0))
  }
  
  def addMovieFeatures(movieSamples:DataFrame, ratingSamples:DataFrame): DataFrame ={

    //add movie basic features
    val samplesWithMovies1 = ratingSamples.join(movieSamples, Seq("movieId"), "left")
    //add release year
    val extractReleaseYearUdf = udf({(title: String) => {
      if (null == title || title.trim.length < 6) {
        1990 // default value
      }
      else {
        val yearString = title.trim.substring(title.length - 5, title.length - 1)
        yearString.toInt
      }
    }})

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "c:\\winutil\\")

    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("featureEngineering")
      .set("spark.submit.deployMode", "client")

    val spark = SparkSession.builder.config(conf).getOrCreate()
    val movieResourcesPath = this.getClass.getResource("/webroot/sampledata/movies.csv")
    val movieSamples = spark.read.format("csv").option("header", "true").load(movieResourcesPath.getPath)
    val ratingsResourcesPath = this.getClass.getResource("/webroot/sampledata/ratings.csv")
    val ratingSamples = spark.read.format("csv").option("header", "true").load(ratingsResourcesPath.getPath)

    println("movieSamples schema: ")
    movieSamples.printSchema()
    movieSamples.show(10)

    println("ratingSamples schema: ")
    ratingSamples.printSchema()
    ratingSamples.show(10)


    val ratingSamplesWithLabel = addSampleLabel(ratingSamples)
    println("display ratingSamplesWithLabel")
    ratingSamplesWithLabel.printSchema()
    ratingSamplesWithLabel.show(10, truncate = false)



//    println("display movieSamples")
//    movieSamples.printSchema()
//    movieSamples.show(10, truncate = false)
//
//
    val samplesWithMovieFeatures = addMovieFeatures(movieSamples, ratingSamplesWithLabel)


//    val samplesWithUserFeatures = addUserFeatures(samplesWithMovieFeatures)


    //save samples as csv format
//    splitAndSaveTrainingTestSamples(samplesWithUserFeatures, "/webroot/sampledata")

    //save user features and item features to redis for online inference
    //extractAndSaveUserFeaturesToRedis(samplesWithUserFeatures)
    //extractAndSaveMovieFeaturesToRedis(samplesWithUserFeatures)
    spark.close()
  }
}
