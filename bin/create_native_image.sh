#!/usr/bin/env bash
mvn clean package
native-image -jar ./target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar hellworld
time java -jar ./target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar
time ./helloworld