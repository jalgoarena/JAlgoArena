job "jalgoarena-queue" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "queue-docker" {
    count = 2

    ephemeral_disk {
      size = 500
    }

    task "jalgoarena-queue" {
      driver = "docker"

      config {
        image         = "jalgoarena/queue:2.4.42"
        network_mode = "host"
      }

      resources {
        cpu    = 512
        memory = 512
        network {
          port "http" {}
        }
      }

      env {
        PORT = "${NOMAD_PORT_http}"
        JAVA_OPTS = "-Xmx400m -Xms50m"
      }

      service {
        name = "jalgoarena-queue"
        tags = ["traefik.frontend.rule=PathPrefixStrip:/queue/api", "secure=false"]
        port = "http"
        check {
          type          = "http"
          path          = "/actuator/health"
          interval      = "10s"
          timeout       = "1s"
        }
      }

      template {
        data = <<EOH
BOOTSTRAP_SERVERS = "{{ range $index, $element := service "kafka1" }}{{ if eq $index 0 }}{{ .Address }}:{{ .Port }}{{ end }}{{ end }},{{ range $index, $element := service "kafka2" }}{{ if eq $index 0 }}{{ .Address }}:{{ .Port }}{{ end }}{{ end }},{{ range $index, $element := service "kafka3" }}{{ if eq $index 0 }}{{ .Address }}:{{ .Port }}{{ end }}{{ end }}"
JALGOARENA_API_URL = "http://{{ range $index, $element := service "traefik" }}{{ if eq $index 0 }}{{ .Address }}:{{ .Port }}{{ end }}{{ end }}"
EOH

        destination = "local/config.env"
        env         = true
      }
    }
  }
}