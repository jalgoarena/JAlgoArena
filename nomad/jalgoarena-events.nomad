job "jalgoarena-events" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "events-docker" {

    ephemeral_disk {
      size = 500
    }

    task "jalgoarena-events" {
      driver = "docker"

      config {
        image = "jalgoarena/events:2.3.28"
        network_mode = "host"
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

        destination = "local/config.env"
        env         = true
      }
    }
  }
}