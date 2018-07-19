job "jalgoarena-traefik" {
  datacenters = ["dc1"]

  type = "system"

  update {
    max_parallel = 1
    healthy_deadline = "3m"
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
        volumes = ["/home/jacek/github/JAlgoArena/nomad/traefik.toml:/etc/traefik/traefik.toml"]
      }

      resources {
        cpu    = 500
        memory = 500
        network {
          port "http" {
            static = 5001
          }
          port "ws" {
            static = 5005
          }
          port "dashboard" {
            static = 15001
          }
        }
      }

      service {
        name = "traefik"
        tags = ["traefik", "traefik.enable=false"]
        port = "http"
        check {
          name      = "service: traefik http check"
          type      = "tcp"
          port      = "http"
          interval  = "10s"
          timeout   = "1s"
        }
        check {
          name      = "service: traefik ws check"
          type      = "tcp"
          port      = "ws"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }
}