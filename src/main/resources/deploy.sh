#!/bin/bash
echo ****start deploy****
#基础参数设置
dir="/home"
name="imethan-blog-2"
jar="imethan-blog-2-dev-2.0.0.jar"
target=$dir/$name/target/$jar

echo dir=$dir name=$name jar=$jar target=$target

#clone源代码
cd $dir
if [ -d $name ];then
 echo ****exec git fetch git merge****
 cd $name
 git fetch
 git merge
else
 echo ****exec git clone****
 git clone https://github.com/ethanwong/imethan-blog-2.git
 cd $name
fi

#编译打包
echo ****exec mevn clean package -DskipTests -P dev****
mvn clean package -DskipTests -P dev

#停止已启动jar
echo ****exec ps -ef kill jar****
ps -ef|grep $name|grep -v grep|awk '{print $2}'|xargs kill -s 9


now=$(date "+%Y%m%d")

#启动jar
echo ****exec java -jar****
exec java -Dfile.encoding=UTF-8 -Dserver.port=80 -jar $target > /home/"$name-$now".log &

echo ****end deploy****
