#!/usr/bin/env bash
docker run -d --net=host --name=consul -v /home/jacek/github/JAlgoArena/consul:/consul/config -e CONSUL_UI_BETA=true consul agent -server -ui -bootstrap-expect=1 -bind=192.168.63.21
echo "Consul started"
docker run -d --net=host --name zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 confluentinc/cp-zookeeper
echo "Zookeeper started"
docker run -d --net=host --name kafka1 -e KAFKA_BROKER_ID=0 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 confluentinc/cp-kafka
echo "Kafka 1 started"
docker run -d --net=host --name kafka2 -e KAFKA_BROKER_ID=1 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9093 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 confluentinc/cp-kafka
echo "Kafka 2 started"
docker run -d --net=host --name kafka3 -e KAFKA_BROKER_ID=2 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9094 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 confluentinc/cp-kafka
echo "Kafka 3 started"

echo "Starting services"
docker run -d --net=host --name=jalgoarena-api spolnik/jalgoarena-api:latest
echo "API Started"
docker run -d --net=host --name=jalgoarena-queue  -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-queue:latest
echo "Queue Started"
docker run -d --net=host --name=jalgoarena-events -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-events:latest
echo "Events Started"
docker run -d --net=host --name=jalgoarena-judge-1 -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-judge:latest
echo "Judge 1 Started"
docker run -d --net=host --name=jalgoarena-judge-2 -e PORT=6001 -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-judge:latest
echo "Judge 2 Started"
docker run -d --net=host --name=jalgoarena-judge-3 -e PORT=6002 -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-judge:latest
echo "Judge 3 Started"
docker run -d --net=host --name=jalgoarena-auth -v /home/jacek/github/JAlgoArena-Auth/UserDetailsStore:/app/UserDetailsStore spolnik/jalgoarena-auth:latest
echo "Auth Started"
docker run -d --net=host --name=jalgoarena-submissions -v /home/jacek/github/JAlgoArena-Submissions/SubmissionsStore:/app/SubmissionsStore spolnik/jalgoarena-submissions:latest
echo "Submissions Started"
docker run -d --net=host --name=jalgoarena-ranking -v /home/jacek/github/JAlgoArena-Ranking/RankingStore:/app/RankingStore spolnik/jalgoarena-ranking:latest
echo "Ranking Started"

echo "Starting UI"
docker run -d --net=host --name=jalgoarena-ui spolnik/jalgoarena-ui:latest
echo "All Started!"