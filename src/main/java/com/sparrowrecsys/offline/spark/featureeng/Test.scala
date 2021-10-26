package com.sparrowrecsys.offline.spark.featureeng

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, sql}
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.feature._
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._


object Test {
  def main(args: Array[String]): Unit = {
//    System.setProperty("hadoop.home.dir", "c:\\winutil\\")
//
//    Logger.getLogger("org").setLevel(Level.ERROR)
//
//    val conf = new SparkConf()
//      .setMaster("local")
//      .setAppName("featureEngineering")
//      .set("spark.submit.deployMode", "client")
//    val spark = SparkSession.builder.config(conf).getOrCreate()
//
//    val df = spark.createDataFrame(Seq(
//      (0.0, 1.0),
//      (1.0, 0.0),
//      (2.0, 1.0),
//      (0.0, 2.0),
//      (0.0, 1.0),
//      (2.0, 0.0)
//    )).toDF("categoryIndex1", "categoryIndex2")
//    df.show()
//
//    val encoder = new OneHotEncoderEstimator()
//      .setInputCols(Array("categoryIndex1", "categoryIndex2"))
//      .setOutputCols(Array("categoryVec1", "categoryVec2"))
//    val model = encoder.fit(df)
//    val encoded = model.transform(df)
//    encoded.show()

    var x = 10
    println(x)

  }

}
