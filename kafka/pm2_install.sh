#!/usr/bin/env bash
rm -rf ~/kafka-data
mkdir ~/kafka-data
mkdir ~/kafka-data/logs_1
mkdir ~/kafka-data/logs_2
mkdir ~/kafka-data/logs_3

rm -rf ~/zookeeper-data
mkdir ~/zookeeper-data

export PATH=/Users/jacek/kafka_2.12-1.1.0/bin:$PATH

pm2 start kafka.config.js
