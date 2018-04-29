module.exports = {
    apps: [
        {
            name: 'zookeeper',
            args: [
                "zookeeper.properties"
            ],
            script: 'zookeeper-server-start.sh'
        },
        {
            name: 'kafka1',
            args: [
                "kafka_1.properties"
            ],
            script: 'kafka-server-start.sh',
            env: {
                JMX_PORT: 10000
            }
        },
        {
            name: 'kafka2',
            args: [
                "kafka_2.properties"
            ],
            script: 'kafka-server-start.sh',
            env: {
                JMX_PORT: 10001
            }
        },
        {
            name: 'kafka3',
            args: [
                "kafka_3.properties"
            ],
            script: 'kafka-server-start.sh',
            env: {
                JMX_PORT: 10002
            }
        }
    ]
};
