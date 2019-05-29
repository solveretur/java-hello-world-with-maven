#!/usr/bin/env bash
mvn clean package
rm -rf ./build
mkdir -p ./build
docker build -t graal -f Dockerfile .
docker run --rm --name graal_lambda -v "/$(pwd)"/build:/root/project/working graal sh -c "cp -r /root/project/build/* /root/project/working"
