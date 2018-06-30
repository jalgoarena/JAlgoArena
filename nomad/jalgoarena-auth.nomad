job "jalgoarena-auth" {
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

  group "auth" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 500
    }

    task "microservice" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-auth:2.2.111"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena/UserDetailsStore:/app/UserDetailsStore"]
      }

      resources {
        cpu    = 1000
        memory = 2000
      }
    }
  }
}