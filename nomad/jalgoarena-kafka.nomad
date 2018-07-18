job "jalgoarena-kafka" {
  datacenters = [
    "dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
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
        cpu = 500
        memory = 500
        network {
          port "zk" {}
        }
      }

      env {
        ZOOKEEPER_CLIENT_PORT = "${NOMAD_PORT_zk}"
      }

      service {
        name = "zookeeper"
        tags = [
          "zookeeper",
          "traefik.enable=false"]
        port = "zk"
        check {
          type = "tcp"
          interval = "10s"
          timeout = "1s"
        }
      }
    }
  }

  group "kafka-docker" {
    count = 3

    ephemeral_disk {
      size = 1000
    }

    task "kafka" {
      driver = "docker"

      config {
        image = "confluentinc/cp-kafka"
        network_mode = "host"
      }

      resources {
        cpu = 750
        memory = 1000
        network {
          port "kafka" {}
        }
      }

      env {
        KAFKA_BROKER_ID = "${NOMAD_ALLOC_INDEX}"
        KAFKA_ADVERTISED_LISTENERS = "PLAINTEXT://${NOMAD_IP_kafka}:${NOMAD_PORT_kafka}"
      }

      service {
        name = "kafka"
        tags = [
          "kafka",
          "traefik.enable=false"]
        port = "kafka"
        check {
          type = "tcp"
          interval = "10s"
          timeout = "1s"
        }
      }

      template {
        data = <<EOH
KAFKA_ZOOKEEPER_CONNECT = "{{ range $index, $zk := service "zookeeper" }}{{ if eq $index 0 }}{{ $zk.Address }}:{{ $zk.Port }}{{ end }}{{ end }}"
EOH

        destination = "local/config.env"
        env = true
      }
    }
  }
}
