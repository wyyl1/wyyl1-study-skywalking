#FROM openjdk:11
FROM openjdk:11-jdk-slim
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
#RUN apt-get update && apt-get install -y \
#    curl
ENV VM_OPTIONS=""
CMD java -jar $VM_OPTIONS web-api-1.0.jar
# 将容器 9900 端口暴露出来， 允许外部连接这个端口
EXPOSE 9900

# ~/temp/docker-dir /Users/aoe/github/wyyl1/wyyl1-study-skywalking/web-api/target/web-api-1.0.jar /Users/aoe/github/wyyl1/wyyl1-study-skywalking/web-api/Dockerfile web-api:1.0.0
# docker image build -t web-api-1.0.0 .

# docker container run --rm -it web-api:1.0.0 /bin/bash

# docker container run -e "VM_OPTIONS=11ee" --rm -it web-api:1.0.0 /bin/bash

# test-sw-web-api