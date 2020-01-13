package com.gitee.code4fun.facerecognition.common.entity;

/**
 * @author yujingze
 * @data 18/7/27
 */
public class HbaseColumn {


    private byte[] family;

    private byte[] qualifier;

    private byte[] value;

    public HbaseColumn(){

    }

    public HbaseColumn(byte[] family,byte[] qualifier,byte[] value){
        this.family = family;
        this.qualifier = qualifier;
        this.value = value;
    }

    public byte[] getFamily() {
        return family;
    }

    public void setFamily(byte[] family) {
        this.family = family;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public void setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

}
