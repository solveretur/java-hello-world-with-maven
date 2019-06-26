#!/usr/bin/env bash
native-image -H:ReflectionConfigurationFiles=./reflection.json -H:ResourceConfigurationFiles=./resources.json -Djava.net.preferIPv4Stack=true --no-server --enable-http --enable-https --enable-url-protocols=http,https --enable-all-security-services -jar ./target/jb-hello-world-maven-0.1.0.jar nat_image
mv nat_image ./build/nat_image
cp ./runtime/bootstrap ./build/bootstrap
cd ./build
chmod +x ./nat_image
chmod +x ./bootstrap
zip runtime.zip nat_image bootstrap
