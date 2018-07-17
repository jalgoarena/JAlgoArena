job "jalgoarena-submissions" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "submissions-docker" {

    ephemeral_disk {
      migrate = true
      size = 1500
      sticky = true
    }

    task "jalgoarena-submissions" {
      driver = "docker"

      config {
        image = "jalgoarena/submissions:2.3.175"
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

        destination = "local/config.env"
        env         = true
      }
    }
  }
}