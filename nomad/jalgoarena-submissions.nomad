job "jalgoarena-submissions" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "submissions-docker" {
    count = 2

    ephemeral_disk {
      migrate = true
      size = 1500
      sticky = true
    }

    task "jalgoarena-submissions" {
      driver = "docker"

      config {
        image = "jalgoarena/submissions:2.4.181"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/SubmissionsStore-${NOMAD_ALLOC_INDEX}:/app/SubmissionsStore"]
      }

      resources {
        cpu    = 750
        memory = 750
        network {
          port "http" {}
        }
      }

      env {
        KAFKA_CONSUMER_GROUP_ID = "submissions-${NOMAD_ALLOC_INDEX}"
        PORT = "${NOMAD_PORT_http}"
        JAVA_OPTS = "-Xmx512m -Xms50m"
      }

      service {
        name = "jalgoarena-submissions"
        tags = ["traefik.frontend.rule=PathPrefixStrip:/submissions/api", "secure=false"]
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
JALGOARENA_API_URL = "http://{{ range $index, $traefik := service "traefik" }}{{ if eq $index 0 }}{{ $traefik.Address }}:{{ $traefik.Port }}{{ end }}{{ end }}"
EOH

        destination = "local/config.env"
        env         = true
      }
    }
  }
}