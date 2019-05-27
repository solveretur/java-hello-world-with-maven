#!/usr/bin/env bash
rm -rf ./build
mkdir -p ./build
mvn clean package
native-image -H:ReflectionConfigurationFiles=./reflection.json -H:ResourceConfigurationFiles=./resources.json --no-server -jar ./target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar nat_image
mv nat_image ./build