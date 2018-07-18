job "jalgoarena-elasticsearch" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "elasticsearch-docker" {

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
          port "elasticsearch" {}
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
  }
}