job "jalgoarena-auth" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "auth-docker" {
    count = 1

    ephemeral_disk {
      migrate = true
      size = 1000
      sticky = true
    }

    task "jalgoarena-auth" {
      driver = "docker"

      config {
        image = "jalgoarena/auth:2.4.134"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/UserDetailsStore:/app/UserDetailsStore"]
      }

      resources {
        cpu    = 750
        memory = 750
        network {
          port "http" {}
        }
      }

      env {
        PORT = "${NOMAD_PORT_http}"
        JAVA_OPTS = "-Xmx512m -Xms50m"
      }

      service {
        name = "jalgoarena-auth"
        tags = ["traefik.frontend.rule=PathPrefixStrip:/auth", "secure=false"]
        port = "http"
        check {
          type          = "http"
          path          = "/actuator/health"
          interval      = "10s"
          timeout       = "1s"
        }
      }
    }
  }
}