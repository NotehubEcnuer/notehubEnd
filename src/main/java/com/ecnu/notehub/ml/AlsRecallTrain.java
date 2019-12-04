package com.ecnu.notehub.ml;



import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author onion
 * @date 2019/11/30 -10:30 上午
 */
public class AlsRecallTrain implements Serializable {
    public static void main(String[] args) throws IOException {
        SparkSession sparkSession = SparkSession.builder().master("local")
                .appName("notehubApp").getOrCreate();
        JavaRDD<String> csv = sparkSession.read().textFile("file:///Users/root1/Desktop/behavior.csv").toJavaRDD();
        JavaRDD<Rating> ratingJavaRDD = csv.map((Function<String, Rating>) Rating::parseRating);
        Dataset<Row> rating = sparkSession.createDataFrame(ratingJavaRDD, Rating.class);
        Dataset<Row>[] split = rating.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainData = split[0];
        Dataset<Row> testData = split[1];
        ALS als = new ALS().setMaxIter(10).setRank(5).setRegParam(0.01)
                .setUserCol("userId").setItemCol("shopId").setRatingCol("rating");
        ALSModel alsModel = als.fit(trainData);
        Dataset<Row> predictions = alsModel.transform(testData);
        RegressionEvaluator evaluator = new RegressionEvaluator().setMetricName("rmse").setLabelCol("rating").setPredictionCol("prediction");
        double rmse = evaluator.evaluate(predictions);
        System.out.println(rmse);
        alsModel.save("file:///Users/root1/Desktop/aslmodel");
    }
    public static class Rating implements Serializable{
        private int userId;
        private int shopId;
        private int rating;
        public static Rating parseRating(String str){
            str = str.replace("\"","");
            String[] strArr = str.split(",");
            int userId = Integer.parseInt(strArr[0]);
            int shopId = Integer.parseInt(strArr[1]);
            int rating = Integer.parseInt(strArr[2]);
            return new Rating(userId, shopId, rating);
        }
        public Rating(int userId, int shopId, int rating) {
            this.userId = userId;
            this.shopId = shopId;
            this.rating = rating;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }
}
