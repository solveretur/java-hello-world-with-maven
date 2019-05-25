#!/usr/bin/env bash
echo "Starting creating native image..."
#mvn clean package
echo "Finished building app. Creating native image directory..."
rm -rf ./build
mkdir -p ./build
docker run --rm --name graal -v $(pwd):/working oracle/graalvm-ce:latest \
sh -c "gu install native-image; \ 
		    native-image \
                    -H:ReflectionConfigurationFiles=/working/reflection.json \
                    -H:ResourceConfigurationFiles=/working/resources.json \
                    -H:+ReportUnsupportedElementsAtRuntime --no-server -jar /working/target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar nat_image \
                    ; \
                    chmod 755 nat_image; \
                    cp nat_image /working/build"

echo "Native image creation finished"
