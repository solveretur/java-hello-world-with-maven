#!/usr/bin/env bash
rm -rf ./build
mkdir -p ./build
mvn clean package
native-image -H:ReflectionConfigurationFiles=./reflection.json -H:ResourceConfigurationFiles=./resources.json -Djava.net.preferIPv4Stack=true --no-server --enable-http --enable-https --enable-url-protocols=http,https --enable-all-security-services -jar ./target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar nat_image
mv nat_image ./build
cp ./runtime/bootstrap ./build/bootstrap
cd ./build
chmod +x ./nat_image
chmod +x ./bootstrap
zip runtime.zip nat_image bootstrap