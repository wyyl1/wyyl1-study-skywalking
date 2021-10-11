FROM openjdk:11
#FROM openjdk:11-jdk-slim
COPY . /skywalking
WORKDIR /skywalking
# 解压后不要包含目录 --strip-components 1
RUN tar -zxvf sw.tar.gz --strip-components 1 && rm -rf sw.tar.gz

# tar -zcvf sw.tar.gz apache-skywalking-apm-es7-8.7.0

# ../issue-docker.sh ~/temp/docker-dir ~/tools/sw.tar.gz Dockerfile test-sw-image:1.0.0

# docker container run --rm -it test-sw-image:1.0.0 /bin/bash