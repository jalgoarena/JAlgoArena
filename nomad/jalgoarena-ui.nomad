job "jalgoarena-ui" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "ui-docker" {

    ephemeral_disk {
      size = 300
    }

    task "jalgoarena-ui" {
      driver = "docker"

      config {
        image = "jalgoarena/ui:2.3.8"
        network_mode = "host"
        network {
          port "http" {
            static = 3000
          }
        }
      }

      resources {
        cpu    = 750
        memory = 750
      }

      service {
        name = "jalgoarena-ui"
        tags = ["ui", "traefik.enable=false"]
        port = "http"
        check {
          type      = "tcp"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }
}