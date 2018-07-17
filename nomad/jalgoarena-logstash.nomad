job "jalgoarena-logstash" {
  datacenters = ["dc1"]

  type = "system"

  update {
    max_parallel = 1
    healthy_deadline = "3m"
    auto_revert = true
  }

  group "logstash-docker" {

    ephemeral_disk {
      size = 1000
    }

    task "logstash" {
      driver = "docker"

      config {
        image = "logstash:5.6.10-alpine"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/logstash:/config-dir"]
        args = [
          "-f", "/config-dir/logstash.conf"
        ]
      }

      resources {
        cpu    = 500
        memory = 750
        network {
          port "logstash" {
            static = 9600
          }
        }
      }

      service {
        name = "logstash"
        tags = ["elk", "traefik.enable=false"]
        port = "logstash"
        check {
          type      = "tcp"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }
}