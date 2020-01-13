#!/bin/bash
ls $1 | while read fileName
do
 echo $1$fileName
 hadoop fs -put $1$fileName $2
done
