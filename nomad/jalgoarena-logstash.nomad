job "jalgoarena-logstash" {
  datacenters = ["dc1"]

  type = "system"

  update {
    max_parallel = 1
    healthy_deadline = "3m"
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
        args = [
          "-f", "local/logstash.conf"
        ]
      }

      resources {
        cpu    = 500
        memory = 750
        network {
          port "tcp" {
            static = 4560
          }
          port "http" {
            static = 9600
          }
        }
      }

      service {
        name = "logstash"
        tags = ["elk", "traefik.enable=false"]
        port = "tcp"
        check {
          type      = "tcp"
          interval  = "10s"
          timeout   = "1s"
        }
      }

      template {
        data = <<EOH
input {
    tcp {
        port => 4560
        codec => json_lines
    }
}

filter {
  date {
    match => [ "timestamp" , "yyyy-MM-dd HH:mm:ss.SSS" ]
  }

  mutate {
    remove_field => ["@version"]
  }
}

output {
  stdout {
    codec => rubydebug
  }

  elasticsearch {
    hosts => [
      "127.0.0.1"
    ]
  }
}
EOH

        destination = "local/logstash.conf"
      }
    }
  }
}