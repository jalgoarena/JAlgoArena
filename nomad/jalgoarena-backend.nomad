job "jalgoarena-backend" {
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

  group "auth-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "jalgoarena-auth" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-auth:2.2.115"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/UserDetailsStore:/app/UserDetailsStore"]
      }

      resources {
        cpu    = 750
        memory = 2000
      }
    }
  }

  group "events-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 500
    }

    task "jalgoarena-events" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-events:2.2.22"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 2000
      }

      env {
        BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094"
      }
    }
  }

  group "judge-docker-1" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "jalgoarena-judge-1" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-judge:2.2.467"
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

  group "judge-docker-2" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "jalgoarena-judge-2" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-judge:2.2.467"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 2000
      }

      env {
        BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094"
        PORT = 6001
      }
    }
  }

  group "queue-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 500
    }

    task "jalgoarena-queue" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-queue:2.2.32"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 2000
      }

      env {
        BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094"
      }
    }
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
        image = "spolnik/jalgoarena-ranking:2.2.41"
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

  group "submissions-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1500
    }

    task "jalgoarena-submissions" {
      driver = "docker"

      config {
        image = "spolnik/jalgoarena-submissions:2.2.157"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/SubmissionsStore:/app/SubmissionsStore"]
      }

      resources {
        cpu    = 750
        memory = 2000
      }

      env {
        BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094"
      }
    }
  }
}