#!/usr/bin/env bash
docker run -d --net=host --name=consul -v /home/jacek/github/JAlgoArena/consul:/consul/config -e CONSUL_UI_BETA=true consul agent -server -ui -bootstrap-expect=1 -bind=192.168.63.21
echo "Consul started"
docker run -d --name zookeeper -p 2181:2181 confluent/zookeeper
echo "Zookeeper started"
docker run -d --name kafka1 -p 9092:9092 --link zookeeper:zookeeper confluent/kafka
echo "Kafka 1 started"
docker run -d --name kafka2 -e KAFKA_BROKER_ID=1 -p 9093:9092 --link zookeeper:zookeeper confluent/kafka
echo "Kafka 2 started"
docker run -d --name kafka3 -e KAFKA_BROKER_ID=2 -p 9094:9092 --link zookeeper:zookeeper confluent/kafka
echo "Kafka 3 started"

echo "Starting services"
docker run -d --name=jalgoarena-api --net=host spolnik/jalgoarena-api:latest
echo "API Started"
docker run -d --name=jalgoarena-queue --net=host -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-queue:latest
echo "Queue Started"
docker run -d --name=jalgoarena-events --net=host -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-events:latest
echo "Events Started"
docker run -d --name=jalgoarena-judge-1 --net=host -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-judge:latest
echo "Judge 1 Started"
docker run -d --name=jalgoarena-judge-2 --net=host -e PORT=6001 -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-judge:latest
echo "Judge 2 Started"
docker run -d --name=jalgoarena-judge-3 --net=host -e PORT=6002 -e BOOTSTRAP_SERVERS=localhost:9092,localhost:9093,localhost:9094 spolnik/jalgoarena-judge:latest
echo "Judge 3 Started"
docker run -d --name=jalgoarena-auth --net=host -v /home/jacek/github/JAlgoArena-Auth/UserDetailsStore:/app/UserDetailsStore spolnik/jalgoarena-auth:latest
echo "Auth Started"
docker run -d --name=jalgoarena-submissions --net=host -v /home/jacek/github/JAlgoArena-Submissions/SubmissionsStore:/app/SubmissionsStore spolnik/jalgoarena-submissions:latest
echo "Submissions Started"
docker run -d --name=jalgoarena-ranking --net=host -v /home/jacek/github/JAlgoArena-Ranking/RankingStore:/app/RankingStore spolnik/jalgoarena-ranking:latest
echo "Submissions Started"