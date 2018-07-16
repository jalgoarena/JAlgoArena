job "jalgoarena-logstash" {
  datacenters = ["dc1"]

  type = "system"

  update {
    max_parallel = 1
    min_healthy_time = "10s"
    healthy_deadline = "3m"
    progress_deadline = "10m"
    auto_revert = false
    canary = 0
  }

  group "logstash-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "logstash" {
      driver = "docker"

      config {
        image = "logstash"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/logstash:/config-dir"]
        args = [
          "-f", "/config-dir/logstash.conf"
        ]
      }

      resources {
        cpu    = 500
        memory = 750
      }

      service {
        name = "logstash"
        tags = ["elk", "traefik.enable=false"]
        port = 9600
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
          interval  = "10s"
          timeout   = "1s"
        }
      }
    }
  }
}