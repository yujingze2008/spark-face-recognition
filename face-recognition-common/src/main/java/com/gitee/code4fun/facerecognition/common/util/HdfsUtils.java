package com.gitee.code4fun.facerecognition.common.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author yujingze
 * @data 18/3/28
 */
public class HdfsUtils {

    private HdfsUtils() {

    }
    /**
     * 新建文件
     *
     * @param filePath
     * @param data
     * @throws IOException
     */
    public static void createFile(String filePath,
                                  byte[] data) throws IOException {
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        FileSystem fs = FileSystem.get(conf);
        FSDataOutputStream outputStream = fs.create(new Path(filePath));
        outputStream.write(data);
        outputStream.close();
        fs.close();
    }

    /**
     * 新建文件
     *
     * @param filePath
     * @param data
     * @throws IOException
     */
    public static void createFile(String filePath,
                                  String data) throws IOException {
        createFile(filePath, data.getBytes());
    }

    /**
     * 从本地上传到HDFS
     * @param localPath 本地文件路径
     * @param remotePath HDF文件S路径
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static void copyFileFromLocal(String localPath,String remotePath) throws IllegalArgumentException, IOException{
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        fs.copyFromLocalFile(new Path(localPath), new Path(remotePath));
    }

    /**
     * 归删除文件
     * @param filePath
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static boolean deleteFileRecursive(String filePath) throws IllegalArgumentException, IOException{
        Configuration conf = new Configuration();
        return deleteFile(filePath,true);
    }

    /**
     * 非递归删除文件
     * @param filePath
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static boolean deleteFile(String filePath) throws IllegalArgumentException, IOException{
        return deleteFile(filePath,false);
    }

    /**
     * 删除文件
     * @param filePath
     * @param recursive
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    private static boolean deleteFile(String filePath,boolean recursive) throws IllegalArgumentException, IOException{
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        return fs.delete(new Path(filePath),recursive);
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static boolean mkdir(String dirPath)
            throws IllegalArgumentException, IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        return fs.mkdirs(new Path(dirPath));
    }

    /**
     * 读取文件内容
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readFile(String filePath)
            throws IOException {
        Configuration conf = new Configuration();
        String res = null;
        FileSystem fs = null;
        FSDataInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            fs = FileSystem.get(conf);
            inputStream = fs.open(new Path(filePath));
            outputStream = new ByteArrayOutputStream(inputStream.available());
            IOUtils.copyBytes(inputStream, outputStream, conf);
            res = outputStream.toString();
        } finally {
            if (inputStream != null){
                IOUtils.closeStream(inputStream);
            }
            if (outputStream != null){
                IOUtils.closeStream(outputStream);
            }
        }
        return res;
    }

    /**
     * 判断路径在HDFS上是否存在
     *
     *            配置
     * @param path
     *            路径
     * @return
     * @throws IOException
     */
    public static boolean exits(String path)
            throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        return fs.exists(new Path(path));
    }

    /**
     * 从HDFS下载文件到本地
     * @param srcPath
     * @param localPath
     * @return
     * @throws IOException
     */
    public static boolean copyFileToLocal(String srcPath,String localPath) throws IOException{
        Configuration conf = new Configuration();
        if(exits(srcPath)){
            FileSystem fs = FileSystem.get(conf);
            InputStream in = fs.open(new Path(srcPath));
            OutputStream out = new FileOutputStream(localPath);
            IOUtils.copyBytes(in,out,4096,true);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 从HDFS批量下载文件到本地
     * @param srcPath
     * @param localPath
     * @throws IOException
     */
    public static void copyFilesToLocal(String srcPath,String localPath) throws IOException{
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        FileStatus[] fstatus = fs.listStatus(new Path(srcPath));
        if(fstatus.length > 0){
            for(FileStatus fileStatus : fstatus){
                String fileName = UUID.randomUUID().toString().replaceAll("-","");
                String remotePath = fileStatus.getPath().toString();
                String fileSuffix = remotePath.substring(remotePath.lastIndexOf("."),remotePath.length());
                copyFileToLocal(remotePath,localPath + fileName  + fileSuffix);
            }
        }
    }

    public static void main(String[] args) throws Exception{

        HdfsUtils.copyFileFromLocal("/Users/yujingze/develop/dockers-bigdata-cluster/docker-compose.yml","/test/");
        Configuration conf = new Configuration();

        DistributedFileSystem dfs = (DistributedFileSystem)FileSystem.get(conf);

        DatanodeInfo[] infso = dfs.getDataNodeStats();

        for(DatanodeInfo info : infso){
            System.out.println(info.getHostName());
        }

    }
}
