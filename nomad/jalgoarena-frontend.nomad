job "jalgoarena-frontend" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    min_healthy_time = "10s"
    healthy_deadline = "3m"
    progress_deadline = "10m"
    auto_revert = false
    canary = 0
  }

  migrate {
    max_parallel = 1
    health_check = "checks"
    min_healthy_time = "10s"
    healthy_deadline = "5m"
  }

  group "traefik-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
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

  group "ui-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "jalgoarena-ui" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-ui:2.2.5"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 1000
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