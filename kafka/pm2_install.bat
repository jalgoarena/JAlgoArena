rm -rf ~/kafka-data
mkdir ~/kafka-data
mkdir ~/kafka-data/logs_1
mkdir ~/kafka-data/logs_2
mkdir ~/kafka-data/logs_3

rm -rf ~/zookeeper-data
mkdir ~/zookeeper-data

pm2 start kafka.config.js
