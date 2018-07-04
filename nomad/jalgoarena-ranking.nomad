job "jalgoarena-ranking" {
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

  group "ranking-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "jalgoarena-ranking" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-ranking:2.3.44"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/RankingStore:/app/RankingStore"]
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