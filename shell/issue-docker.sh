#!/bin/bash
# 发布 jar 包到 Docker 镜像
# 参数说明
# $1 准备打包 Docker 镜像的目录
# $2 jar 文件路径
# $3 Dockerfile 文件路径

CURRENT_DIR=$(pwd)

docker_dir=$1
jar_path=$2
dockerfile_path=$3
image_name=$4

echo $docker_dir
echo $jar_path
echo $dockerfile_path

rm -rf $docker_dir
mkdir $docker_dir
cp $jar_path $docker_dir
cp $dockerfile_path $docker_dir
cd $docker_dir
echo '-------------------------------'
echo $(pwd)
echo $dockerfile_path
ls
echo '-------------------------------'
docker image build --no-cache -t $image_name .

cd $CURRENT_DIR