package com.gitee.code4fun.facerecognition.common.util;

import com.gitee.code4fun.facerecognition.common.entity.HbaseColumn;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yujingze
 * @data 18/7/27
 */
public class HbaseUtils {

    protected static Connection connection = null;

    static{
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.client.retries.number", "3");
        conf.set("hbase.rpc.timeout", "5000");
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try{
            System.out.println(connection.getAdmin().getClusterStatus().getHBaseVersion());
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    public static void put(String tableName,String rowKey,List<HbaseColumn> columns) throws Exception{
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        columns.forEach(column -> {
            put.addColumn(column.getFamily(),column.getQualifier(),column.getValue());
        });
        table.put(put);
    }


    public static List<String> get(String tableName,String rowKey,String... cfs) throws Exception{
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        Result result = table.get(get);
        List<String> list = new ArrayList<>();
        for(String cf : cfs){
            String[] arrary = cf.split(":");
            String family = arrary[0];
            String qulifier = arrary[1];
            byte[] bytes = result.getValue(family.getBytes(),qulifier.getBytes());
            list.add(new String(bytes));
        }
        return list;
    }


    public static void testAdmin() throws Exception{
        Admin admin = connection.getAdmin();
        admin.getClusterStatus();
    }

    public static void main(String[] args) throws Exception{
        //List columns = Arrays.asList(new HbaseColumn("cf".getBytes(),"tt".getBytes(),"myvalue".getBytes()));
        //HbaseUtils.put("test","12345",columns);
        //001581ca-9c42-4fec-9fd7-c19ecfb0d526
        /*List<String> result = HbaseUtils.get("t_images","row1","cf:data");
        System.out.println(result.get(0));*/
        HbaseUtils.testAdmin();
    }

}
