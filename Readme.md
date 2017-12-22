# Collecting Metrics from the RESTful services and collect/store in graphite.

## Building docker container for java
 `java` folder contains the Dockerfile. To build `java` image `docker build -t myjava .`
 
## Run the jar
 Run the java jar from the `javaContainer`. To build the java runnable use `docker build -t javacontainer .`
 
 The whole process is scripted in the `startup.sh`. 
 
 
 # Build the graphite container
  Checkout https://github.com/bipinupd/GraphiteGrafanaInDocker and build the container.
 