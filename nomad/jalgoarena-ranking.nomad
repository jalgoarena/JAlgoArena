job "jalgoarena-ranking" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "ranking-docker" {
    count = 2

    ephemeral_disk {
      migrate = true
      size = 1000
      sticky = true
    }

    task "jalgoarena-ranking" {
      driver = "docker"

      config {
        image = "jalgoarena/ranking:2.4.53"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/RankingStore-${NOMAD_ALLOC_INDEX}:/app/RankingStore"]
      }

      resources {
        cpu    = 1000
        memory = 750
        network {
          port "http" {}
        }
      }

      env {
        KAFKA_CONSUMER_GROUP_ID = "ranking-${NOMAD_ALLOC_INDEX}"
        PORT = "${NOMAD_PORT_http}"
        JAVA_OPTS = "-Xmx512m -Xms50m"
      }

      service {
        name = "jalgoarena-ranking"
        tags = ["traefik.frontend.rule=PathPrefixStrip:/ranking/api", "secure=false"]
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