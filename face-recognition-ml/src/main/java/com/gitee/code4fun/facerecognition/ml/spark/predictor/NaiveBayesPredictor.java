package com.gitee.code4fun.facerecognition.ml.spark.predictor;

import com.gitee.code4fun.facerecognition.ml.spark.MlConstants;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @author yujingze
 * @data 18/3/26
 */
public class NaiveBayesPredictor {

    private static Logger logger = LoggerFactory.getLogger(NaiveBayesPredictor.class);

    public static void main(String[] args) throws Exception {
        final String appName = "NaiveBayesPredictor";
        /**
         * 接收三个参数
         * master 提交模式
         * modelPath 模型的hdfs路径
         * predictPath 特征向量的hdfs路径
         * outputPath 预测结果的hdfs导出路径
         */
        if (args.length != 4) {
            System.out.println("This program requires four parameters，" +
                    "please input 'master' 'modelPath' 'predictPath' 'outputPath'");
        } else {
            String master = args[0];
            String modelPath = args[1];
            String predictPath = args[2];
            String outputPath = args[3];

            SparkConf conf = new SparkConf();
            JavaSparkContext sc = new JavaSparkContext(master, appName, conf);
            //获取模型
            final NaiveBayesModel model = NaiveBayesModel.load(sc.sc(), MlConstants.HDFS_PREFIX + modelPath);
            //生成模型向量
            JavaRDD<Double> predictRdd = sc.textFile(MlConstants.HDFS_PREFIX + predictPath)
                    .flatMap(new FlatMapFunction<String, String>() {
                        public Iterable<String> call(String s) throws Exception {
                            return Arrays.asList(s.split(" "));
                        }
                    }).map(new Function<String, Double>() {
                        public Double call(String s) throws Exception {
                            return Double.valueOf(s);
                        }
                    });
            //转向量数组
            List<Double> predictList = predictRdd.collect();
            double[] dist = new double[predictList.size()];
            for (int i = 0; i < predictList.size(); i++) {
                dist[i] = predictList.get(i);
            }
            Vector vec = Vectors.dense(dist);
            //预测
            double userId = model.predict(vec);
            Vector prob = model.predictProbabilities(vec);
            //保存结果
            FileSystem fs = FileSystem.get(new URI(MlConstants.HDFS_PREFIX), new HdfsConfiguration());
            FSDataOutputStream outputStream = fs.create(new Path(MlConstants.HDFS_PREFIX + outputPath));
            outputStream.write(String.valueOf(userId).getBytes());
            outputStream.close();
            fs.close();
            //输出结果
            System.out.println("********* the preidct result is " + userId);
            System.out.println("********* predictProbabilities is " + prob);

        }
    }

}
