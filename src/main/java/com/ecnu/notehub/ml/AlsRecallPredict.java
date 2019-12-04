package com.ecnu.notehub.ml;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author onion
 * @date 2019/11/30 -11:17 上午
 */
public class AlsRecallPredict {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local")
                .appName("notehubApp").getOrCreate();
        ALSModel alsModel = ALSModel.load("file:///Users/root1/Desktop/aslmodel");
        JavaRDD<String> csvFile = sparkSession.read().textFile("file:///Users/root1/Desktop/behavior.csv").toJavaRDD();
        JavaRDD<Rating> ratingJavaRDD = csvFile.map((Function<String, Rating>) Rating::parseRating);
        Dataset<Row> rating = sparkSession.createDataFrame(ratingJavaRDD, Rating.class);
        Dataset<Row> users = rating.select(alsModel.getUserCol()).distinct().limit(5);
        Dataset<Row> userRecs = alsModel.recommendForUserSubset(users, 20);
        userRecs.foreachPartition((ForeachPartitionFunction<Row>) iterator -> {
            //数据库连接，插入数据库中
            List<Map<String, Object>> data = new ArrayList<>();
            iterator.forEachRemaining(action->{
                int userId = action.getInt(0);
                List<GenericRowWithSchema> recommendList = action.getList(1);
                List<Integer> shopIdList = new ArrayList<>();
                recommendList.forEach(row ->{
                    Integer shopId = row.getInt(0);
                    shopIdList.add(shopId);
                });
//                String recommendData = StringUtils.join(shopIdList, ",");
                String recommendData = "";
                Map<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("recommend", recommendData);
                data.add(map);
            });
            //存数据库
            data.forEach(e->{});
        });

    }
    public static class Rating implements Serializable {
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
