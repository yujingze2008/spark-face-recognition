package com.gitee.code4fun.facerecognition.ml.spark.classifier;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import scala.Tuple2;


/**
 * @author yujingze
 * @data 18/3/26
 */
public class NaiveBayesClassifier2 {


    public static void main(String[] args) throws Exception {
        final String appName = "NaiveBayesClassifier";

        SparkConf sparkConf = new SparkConf();
        JavaSparkContext jsc = new JavaSparkContext("local[*]",appName, sparkConf);

        Configuration conf = HBaseConfiguration.create();

        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("cf"));
        scan.addColumn(Bytes.toBytes("cf"),Bytes.toBytes("data"));

        /*Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("_tag"));

        scan.setFilter(filter);*/

        conf.set(TableInputFormat.INPUT_TABLE,"t_vector");

        ClientProtos.Scan proto = ProtobufUtil.toScan(scan);

        String scanToString = Base64.encodeBytes(proto.toByteArray());

        conf.set(TableInputFormat.SCAN,scanToString);

        JavaPairRDD<ImmutableBytesWritable, Result> myRDD = jsc.newAPIHadoopRDD(conf, TableInputFormat.class,
                ImmutableBytesWritable.class, Result.class);

        System.out.println("*****************:::::"+myRDD.count());

        myRDD.foreach(new VoidFunction<Tuple2<ImmutableBytesWritable, Result>>() {
            @Override
            public void call(Tuple2<ImmutableBytesWritable, Result> immutableBytesWritableResultTuple2) throws Exception {
                System.out.println(new String(immutableBytesWritableResultTuple2._2().getRow()));
            }
        });

        JavaRDD<LabeledPoint> inputData = myRDD.map(new Function<Tuple2<ImmutableBytesWritable,Result>, LabeledPoint>() {
            @Override
            public LabeledPoint call(Tuple2<ImmutableBytesWritable, Result> immutableBytesWritableResultTuple2) throws Exception {
                Result result = immutableBytesWritableResultTuple2._2();
                String rowKey = new String(result.getRow());
                String data = new String(result.getValue("cf".getBytes(),"data".getBytes()));
                String category = rowKey.split("_")[1];
                String[] datas = data.split(" ");
                double[] values = new double[datas.length-1];
                for(int i=1;i<datas.length-1;i++){
                    values[i] = Double.valueOf(datas[i]);
                }
                Vector vector = Vectors.dense(values);
                LabeledPoint labeledPoint = new LabeledPoint(Double.valueOf(category),vector);
                return labeledPoint;
            }
        });

        System.out.println("*****************:::::"+inputData.count());

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

        /*JavaRDD<LabeledPoint> inputData = MLUtils.loadLabeledPoints(jsc.sc(), MlConstants.HDFS_PREFIX + samplePath).toJavaRDD();
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
        if (hdfs.exists(modelHdfsPath)) {
            hdfs.delete(modelHdfsPath, true);
        }

        model.save(jsc.sc(), MlConstants.HDFS_PREFIX + modelPath);*/


    }

}
