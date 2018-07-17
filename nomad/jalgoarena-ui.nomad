job "jalgoarena-ui" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
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
      }

      resources {
        cpu    = 750
        memory = 750
      }

      service {
        name = "jalgoarena-ui"
        tags = ["ui", "traefik.enable=false"]
        address_mode = "driver"
        port = 3000
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