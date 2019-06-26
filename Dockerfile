FROM oracle/graalvm-ce:latest

RUN gu install native-image
RUN yum -y install zip
RUN yum -y install dos2unix

RUN mkdir -p /root/project
RUN mkdir -p /root/project/build

COPY target/jb-hello-world-maven-0.1.0.jar /root/project/target/jb-hello-world-maven-0.1.0.jar
COPY reflection.json /root/project/reflection.json
COPY resources.json /root/project/resources.json
COPY runtime/bootstrap /root/project/runtime/bootstrap
COPY bin/create_native_image.sh /root/project/bin/create_native_image.sh

WORKDIR /root/project
RUN dos2unix -k bin/create_native_image.sh
RUN chmod +x bin/create_native_image.sh
RUN sh bin/create_native_image.sh
