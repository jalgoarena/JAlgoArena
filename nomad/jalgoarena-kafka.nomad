job "jalgoarena-kafka" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "zk-docker" {

    ephemeral_disk {
      size = 1000
    }

    task "zookeeper" {
      driver = "docker"

      config {
        image = "confluentinc/cp-zookeeper"
        network_mode = "host"
      }

      resources {
        cpu    = 500
        memory = 500
      }

      env {
        ZOOKEEPER_CLIENT_PORT = 2181
      }

      service {
        name = "zookeeper"
        tags = ["zookeeper", "traefik.enable=false"]
        port = 2181
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }

  group "kafka-docker-1" {

    ephemeral_disk {
      size = 1000
    }

    task "kafka-1" {
      driver = "docker"

      config {
        image = "confluentinc/cp-kafka"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 1000
      }

      env {
        KAFKA_BROKER_ID = 0
        KAFKA_ADVERTISED_LISTENERS = "PLAINTEXT://localhost:9092"
        KAFKA_ZOOKEEPER_CONNECT = "localhost:2181"
      }

      service {
        name = "kafka1"
        tags = ["kafka", "traefik.enable=false"]
        port = 9092
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }

  group "kafka-docker-2" {

    ephemeral_disk {
      size = 1000
    }

    task "kafka-2" {
      driver = "docker"

      config {
        image = "confluentinc/cp-kafka"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 1000
      }

      env {
        KAFKA_BROKER_ID = 1
        KAFKA_ADVERTISED_LISTENERS = "PLAINTEXT://localhost:9093"
        KAFKA_ZOOKEEPER_CONNECT = "localhost:2181"
      }

      service {
        name = "kafka2"
        tags = ["kafka", "traefik.enable=false"]
        port = 9093
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }

  group "kafka-docker-3" {

    ephemeral_disk {
      size = 1000
    }

    task "kafka-3" {
      driver = "docker"

      config {
        image = "confluentinc/cp-kafka"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 1000
      }

      env {
        KAFKA_BROKER_ID = 2
        KAFKA_ADVERTISED_LISTENERS = "PLAINTEXT://localhost:9094"
        KAFKA_ZOOKEEPER_CONNECT = "localhost:2181"
      }

      service {
        name = "kafka3"
        tags = ["kafka", "traefik.enable=false"]
        port = 9094
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }
}