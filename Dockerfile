FROM oracle/graalvm-ce:latest

RUN gu install native-image
RUN yum -y install zip

RUN mkdir /root/project
WORKDIR /root/project

COPY target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar /root/project/target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar
COPY runtime/bootstrap /root/project/runtime/bootstrap
COPY bin/create_native_image.sh /root/project/bin/create_native_image.sh

RUN cd /root/project ; ./bin/create_native_image.sh
