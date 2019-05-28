#!/usr/bin/env bash
mvn clean package
rm -rf ./build
mkdir -p ./build
./bin/create_native_image.sh