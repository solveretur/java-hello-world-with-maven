#!/usr/bin/env bash
mvn clean package
native-image -H:ReflectionConfigurationFiles=./reflection.json -H:IncludeResources=org/joda/time/tz/data/ZoneInfoMap --no-server -jar ./target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar hellworld