job "jalgoarena-traefik" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "traefik-docker" {

    ephemeral_disk {
      size = 300
    }

    task "traefik" {
      driver = "docker"

      config {
        image = "traefik"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/traefik.toml:/etc/traefik/traefik.toml"]
      }

      resources {
        cpu    = 500
        memory = 500
      }

      service {
        name = "traefik"
        tags = ["traefik", "traefik.enable=false"]
        address_mode = "driver"
        port = 5001
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