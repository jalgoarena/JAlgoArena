job "jalgoarena-judge" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "judge-docker" {
    count = 2

    ephemeral_disk {
      size = 300
    }

    task "jalgoarena-judge" {
      driver = "docker"

      config {
        image = "jalgoarena/judge:2.4.480"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 1500
        network {
          port "http" {}
        }
      }

      env {
        PORT = "${NOMAD_PORT_http}"
        JAVA_OPTS = "-Xmx1g -Xms512m"
      }

      service {
        name = "jalgoarena-judge"
        tags = ["traefik.frontend.rule=PathPrefixStrip:/judge/api", "secure=false"]
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
BOOTSTRAP_SERVERS = "{{ range $index, $kafka := service "kafka" }}{{ if eq $index 0 }}{{ $kafka.Address }}:{{ $kafka.Port }}{{ else}},{{ $kafka.Address }}:{{ $kafka.Port }}{{ end }}{{ end }}"
EOH

        destination = "judge/config.env"
        env         = true
      }
    }
  }
}