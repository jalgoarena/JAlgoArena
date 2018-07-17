job "jalgoarena-judge" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "judge-docker-1" {

    ephemeral_disk {
      size = 300
    }

    task "jalgoarena-judge-1" {
      driver = "docker"

      config {
        image = "jalgoarena/judge:2.3.475"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 1500
      }

      env {
        JAVA_OPTS = "-Xmx1g -Xms512m"
      }

      template {
        data = <<EOH
BOOTSTRAP_SERVERS = "{{ range service "kafka1" }}{{ .Address }}:{{ .Port }}{{ end }},{{ range service "kafka2" }}{{ .Address }}:{{ .Port }}{{ end }},{{ range service "kafka3" }}{{ .Address }}:{{ .Port }}{{ end }}"
EOH

        destination = "judge/config.env"
        env         = true
      }
    }
  }

  group "judge-docker-2" {

    ephemeral_disk {
      size = 300
    }

    task "jalgoarena-judge-2" {
      driver = "docker"

      config {
        image = "jalgoarena/judge:2.3.475"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 1500
      }

      env {
        JAVA_OPTS = "-Xmx1g -Xms512m"
        PORT = 6001
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