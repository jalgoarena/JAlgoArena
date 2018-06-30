job "jalgoarena-events" {
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

  group "events" {
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
        image = "spolnik/jalgoarena-events:2.2.20"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 2000
      }

      env {
        BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094"
      }
    }
  }
}