#!/usr/bin/env bash
echo "Starting creating native image..."
#mvn clean package
echo "Finished building app. Creating native image directory..."
docker run --rm --name graal -v $(pwd):/working oracle/graalvm-ce:latest \
/bin/bash -c "native-image \
                    -H:ReflectionConfigurationFiles=/working/reflection.json \
                    -H:ResourceConfigurationFiles=/working/resources.json \
                    -H:+ReportUnsupportedElementsAtRuntime --no-server -jar /working/target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar hellworld \
                    ; \
                    cp hellworld /working/build/server"

echo "Copying native image from graalvm container..."
rm -rf build/package
mkdir build/package

cp build/server build/package

echo "Native image creation finished"
