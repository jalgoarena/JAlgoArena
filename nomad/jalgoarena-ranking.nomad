job "jalgoarena-ranking" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "ranking-docker" {

    ephemeral_disk {
      migrate = true
      size = 1000
      sticky = true
    }

    task "jalgoarena-ranking" {
      driver = "docker"

      config {
        image = "jalgoarena/ranking:2.3.49"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/RankingStore:/app/RankingStore"]
      }

      resources {
        cpu    = 1000
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