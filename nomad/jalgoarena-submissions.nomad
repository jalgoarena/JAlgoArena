job "jalgoarena-submissions" {
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
        image = "jalgoarena/submissions:2.3.172"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/SubmissionsStore:/app/SubmissionsStore"]
      }

      resources {
        cpu    = 750
        memory = 750
      }

      env {
        JAVA_OPTS = "-Xmx512m -Xms50m"
      }

      template {
        data = <<EOH
BOOTSTRAP_SERVERS = "{{ range service "kafka1" }}{{ .Address }}:{{ .Port }}{{ end }},{{ range service "kafka2" }}{{ .Address }}:{{ .Port }}{{ end }},{{ range service "kafka3" }}{{ .Address }}:{{ .Port }}{{ end }}"
EOH

        destination = "submissions/config.env"
        env         = true
      }
    }
  }
}