job "jalgoarena-events" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "events-docker" {
    count = 2

    ephemeral_disk {
      size = 300
    }

    task "jalgoarena-events" {
      driver = "docker"

      config {
        image = "jalgoarena/events:2.4.32"
        network_mode = "host"
      }

      resources {
        cpu    = 500
        memory = 512
        network {
          port "http" {}
        }
      }

      env {
        KAFKA_CONSUMER_GROUP_ID = "events-${NOMAD_ALLOC_INDEX}"
        PORT = "${NOMAD_PORT_http}"
        JAVA_OPTS = "-Xmx400m -Xms50m"
      }

      service {
        name = "jalgoarena-events"
        tags = ["secure=false"]
        port = "http"
        check {
          type          = "http"
          path          = "/actuator/health"
          interval      = "10s"
          timeout       = "1s"
        }
      }

      template {
        data = <<EOH
BOOTSTRAP_SERVERS = "{{ range service "kafka1" }}{{ .Address }}:{{ .Port }}{{ end }},{{ range service "kafka2" }}{{ .Address }}:{{ .Port }}{{ end }},{{ range service "kafka3" }}{{ .Address }}:{{ .Port }}{{ end }}"
EOH

        destination = "local/config.env"
        env         = true
      }
    }
  }
}