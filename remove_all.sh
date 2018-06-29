#!/usr/bin/env bash
echo "Cleanup existing containers"
docker stop jalgoarena-ui
docker stop jalgoarena-queue
docker stop jalgoarena-events
docker stop jalgoarena-judge-1
docker stop jalgoarena-judge-2
docker stop jalgoarena-judge-3
docker stop jalgoarena-auth
docker stop jalgoarena-submissions
docker stop jalgoarena-ranking
docker stop traefik
docker stop kafka1
docker stop kafka2
docker stop kafka3
docker stop zookeeper
docker stop consul

docker rm consul
docker rm zookeeper
docker rm kafka1
docker rm kafka2
docker rm kafka3
docker rm traefik
docker rm jalgoarena-queue
docker rm jalgoarena-events
docker rm jalgoarena-judge-1
docker rm jalgoarena-judge-2
docker rm jalgoarena-judge-3
docker rm jalgoarena-auth
docker rm jalgoarena-submissions
docker rm jalgoarena-ranking
docker rm jalgoarena-ui