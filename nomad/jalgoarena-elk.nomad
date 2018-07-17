job "jalgoarena-elk" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "elk-docker" {

    ephemeral_disk {
      size = 2000
    }

    task "elasticsearch" {
      driver = "docker"

      config {
        image = "elasticsearch:5.6.10-alpine"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 3000
        network {
          port "elasticsearch" {
            static = 9200
          }
        }
      }

      service {
        name = "elasticsearch"
        tags = ["elk", "traefik.enable=false"]
        port = "elasticsearch"
        check {
          type      = "tcp"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }

    task "kibana" {
      driver = "docker"

      config {
        image = "kibana:5.6.10"
        network_mode = "host"
      }

      resources {
        cpu    = 500
        memory = 500
        network {
          port "kibana" {
            static = 5601
          }
        }
      }

      service {
        name = "kibana"
        tags = ["elk", "traefik.enable=false"]
        port = "kibana"
        check {
          type      = "tcp"
          interval  = "10s"
          timeout   = "1s"
        }
      }

      env {
        ELASTICSEARCH_URL = "http://localhost:9200"
      }
    }
  }
}