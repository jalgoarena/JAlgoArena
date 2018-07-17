job "jalgoarena-elk" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "elasticsearch-docker" {

    ephemeral_disk {
      size = 2000
    }

    task "elasticsearch" {
      driver = "docker"

      config {
        image = "elasticsearch"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 3000
      }

      service {
        name = "elasticsearch"
        tags = ["elk", "traefik.enable=false"]
        port = 9200
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

  group "kibana-docker" {

    ephemeral_disk {
      size = 1000
    }

    task "kibana" {
      driver = "docker"

      config {
        image = "kibana"
        network_mode = "host"
      }

      resources {
        cpu    = 500
        memory = 500
      }

      service {
        name = "kibana"
        tags = ["elk", "traefik.enable=false"]
        port = 5601
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
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