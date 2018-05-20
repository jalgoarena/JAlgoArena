module.exports = {
    apps: [
        {
            name: 'zookeeper',
            args: [
                "zookeeper.windows.properties"
            ],
            script: 'C:\\Users\\Jacek\\Tools\\kafka_2.12-1.1.0\\bin\\windows\\zookeeper-server-start.bat'
        },
        {
            name: 'kafka1',
            args: [
                "kafka_1.windows.properties"
            ],
            script: 'C:\\Users\\Jacek\\Tools\\kafka_2.12-1.1.0\\bin\\windows\\kafka-server-start.bat',
            env: {
                JMX_PORT: 10000
            }
        },
        {
            name: 'kafka2',
            args: [
                "kafka_2.windows.properties"
            ],
            script: 'C:\\Users\\Jacek\\Tools\\kafka_2.12-1.1.0\\bin\\windows\\kafka-server-start.bat',
            env: {
                JMX_PORT: 10001
            }
        },
        {
            name: 'kafka3',
            args: [
                "kafka_3.windows.properties"
            ],
            script: 'C:\\Users\\Jacek\\Tools\\kafka_2.12-1.1.0\\bin\\windows\\kafka-server-start.bat',
            env: {
                JMX_PORT: 10002
            }
        }
    ]
};
