package com.gitee.code4fun.facerecognition.ml.spark.streaming;

import com.gitee.code4fun.facerecognition.ml.spark.JedisUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @author yujingze
 * @data 18/7/23
 */
public class KafkaStreaming {

    private static Producer<String,String> producer = null;

    static{
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093,localhost:9094");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer(properties);
    }

    public static void main(String[] args) throws Exception{
        Logger.getLogger("org").setLevel(Level.ERROR);

        SparkConf conf = new SparkConf();
        conf.setAppName("streams4rules4");

        JavaStreamingContext jsc = new JavaStreamingContext(conf,new Duration(100));


        Map<String,Integer> topicmap = new HashMap<String,Integer>();
        topicmap.put("rules_event",3);
        String zk = "localhost:2181,localhost:2182,localhost:2183";
        JavaPairReceiverInputDStream messages = KafkaUtils.createStream(jsc,zk,"streams4rules",topicmap);

        JavaDStream<String> lines = messages.map(new Function<Tuple2<String,String>,String>() {

            @Override
            public String call(Tuple2<String, String> tp2) throws Exception {
                return tp2._2();
            }
        });


        lines.foreachRDD(rdd -> {
            rdd.foreach(rs -> {
                System.out.println("!!!!!!!!!"+rs);
                ProducerRecord record = new ProducerRecord("face_result",rs + ":" +new Random().nextInt(10000));
                producer.send(record);

            });
        });


        //lines.print();

        jsc.start();
        jsc.awaitTermination();

    }

}
