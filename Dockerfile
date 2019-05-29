FROM oracle/graalvm-ce:latest

RUN gu install native-image
RUN yum -y install zip

RUN mkdir /root/project

COPY target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar /root/project/target/jb-hello-world-maven-0.1.0-jar-with-dependencies.jar
COPY runtime/bootstrap /root/project/runtime/bootstrap
COPY bin/create_native_image.sh /root/project/bin/create_native_image.sh

WORKDIR /root/project
RUN ls runtime
RUN ls bin
RUN ls target
RUN chmod +x bin/create_native_image.sh
RUN bin/create_native_image.sh
