package com.ecnu.notehub.ml;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;

/**
 * @author onion
 * @date 2019/11/30 -11:49 上午
 */
public class LRTrain {
    public static void main(String[] args) throws IOException {
        SparkSession sparkSession = SparkSession.builder().master("local")
                .appName("notehubApp").getOrCreate();
        JavaRDD<String> csv = sparkSession.read().textFile("file:///Users/root1/Desktop/behavior.csv").toJavaRDD();
        JavaRDD<Row> rowJavaRDD = csv.map(new Function<String, Row>() {
            @Override
            public Row call(String s) throws Exception {
                s = s.replace("\"","");
                String[] split = s.split(",");
                return RowFactory.create(new Double(split[11]), Vectors.dense(Double.valueOf(split[0]),
                        Double.valueOf(split[1]),Double.valueOf(split[2]),Double.valueOf(split[3]),
                        Double.valueOf(split[4]),Double.valueOf(split[5]),Double.valueOf(split[6]),
                        Double.valueOf(split[7]),Double.valueOf(split[8]),Double.valueOf(split[9])));
            }
        });
        StructType schema = new StructType(
                new StructField[]{
                        new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                        new StructField("features", new VectorUDT(), false, Metadata.empty())
                }
        );
        Dataset<Row> data = sparkSession.createDataFrame(rowJavaRDD, schema);
        Dataset<Row>[] datasets = data.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainData = datasets[0];
        Dataset<Row> testData = datasets[1];
        LogisticRegression lr = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8).setFamily("multinomial");
        LogisticRegressionModel model = lr.fit(trainData);
        model.save("file:///Users/root1/Desktop/lsmodel");
        Dataset<Row> predictions = model.transform(testData);
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator();
        double accuracy = evaluator.setMetricName("accuracy").evaluate(predictions);
        System.out.println(accuracy);
    }
}
