#!/bin/bash
currentpwd=`pwd`

# Build base java image
cd java/
docker build -t myjava .

# Build Java project
cd $currentpwd
cd MetricsProject/
mvn clean install

## Copy the Jar project

cd $currentpwd

cp MetricsProject/target/*.jar javaContainer/runnable.jar

cd javaContainer/
docker build -t javacontainer .

docker run -p 8090:8090 javacontainer 