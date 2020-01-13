package com.gitee.code4fun.facerecognition.ml.spark.classifier;


import com.gitee.code4fun.facerecognition.ml.spark.MlConstants;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple2;

import java.net.URI;


/**
 * @author yujingze
 * @data 18/3/26
 */
public class NaiveBayesClassifier {


    public static void main(String[] args) throws Exception{
        final String appName = "NaiveBayesClassifier";
        /**
         * 接受三个参数
         * master 提交模式
         * samplePath 训练样本集的路径
         * modelPath 模型保存路径
         */
        if (args.length != 3) {
            System.out.println("This program requires two parameters，" +
                    "please input 'samplePath' 'modelPath'");
        } else {
            String master = args[0];
            String samplePath = args[1];
            String modelPath = args[2];

            SparkConf sparkConf = new SparkConf();
            JavaSparkContext jsc = new JavaSparkContext(master,appName,sparkConf);

            JavaRDD<LabeledPoint> inputData = MLUtils.loadLabeledPoints(jsc.sc(), MlConstants.HDFS_PREFIX + samplePath).toJavaRDD();
            JavaRDD<LabeledPoint>[] tmp = inputData.randomSplit(new double[]{0.8, 0.2});
            JavaRDD<LabeledPoint> training = tmp[0]; // training set
            JavaRDD<LabeledPoint> test = tmp[1]; // test set
            final NaiveBayesModel model = NaiveBayes.train(training.rdd(), 1.0);
            JavaPairRDD<Double, Double> predictionAndLabel =
                    test.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
                        public Tuple2<Double, Double> call(LabeledPoint p) {
                            return new Tuple2<Double, Double>(model.predict(p.features()), p.label());
                        }
                    });
            double accuracy = predictionAndLabel.filter(new Function<Tuple2<Double, Double>, Boolean>() {
                public Boolean call(Tuple2<Double, Double> pl) {
                    return pl._1().equals(pl._2());
                }
            }).count() / (double) test.count();

            System.out.println("this accuracy:" + accuracy);

            //保存模型
            FileSystem hdfs = FileSystem.get(new URI(MlConstants.HDFS_PREFIX), new HdfsConfiguration());
            Path modelHdfsPath = new Path(modelPath);
            if(hdfs.exists(modelHdfsPath)){
                hdfs.delete(modelHdfsPath,true);
            }

            model.save(jsc.sc(), MlConstants.HDFS_PREFIX + modelPath);

        }

    }

}
