job "jalgoarena-ui" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "ui-docker" {

    ephemeral_disk {
      size = 300
    }

    task "jalgoarena-ui" {
      driver = "docker"

      config {
        image = "jalgoarena/ui:2.4.0"
        network_mode = "host"
      }

      resources {
        cpu    = 750
        memory = 750
        network {
          port "http" {
            static = 3000
          }
        }
      }

      service {
        name = "jalgoarena-ui"
        tags = ["ui", "traefik.enable=false"]
        port = "http"
        check {
          type      = "tcp"
          interval  = "10s"
          timeout   = "1s"
        }
      }

      template {
        data = <<EOH
JALGOARENA_API_URL = "http://{{ range $index, $traefik := service "traefik" }}{{ if eq $index 0 }}{{ $traefik.Address }}:{{ $traefik.Port }}{{ end }}{{ end }}"
JALGOARENA_WS_URL = "http://{{ range $index, $events := service "jalgoarena-events" }}{{ if eq $index 0 }}{{ $events.Address }}:{{ $events.Port }}{{ end }}{{ end }}"
EOH

        destination = "local/config.env"
        env         = true
      }
    }
  }
}