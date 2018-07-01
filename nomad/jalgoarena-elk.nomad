job "jalgoarena-elk" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    min_healthy_time = "10s"
    healthy_deadline = "3m"
    progress_deadline = "10m"
    auto_revert = false
    canary = 0
  }

  migrate {
    max_parallel = 1
    health_check = "checks"
    min_healthy_time = "10s"
    healthy_deadline = "5m"
  }

  group "elasticsearch-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 2000
    }

    task "elasticsearch" {
      driver = "docker"

      config {
        image = "elasticsearch"
        network_mode = "host"
      }

      resources {
        cpu    = 1000
        memory = 3000
      }

      service {
        name = "elastichsearch"
        tags = ["elk"]
        port = 9200
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

  group "kibana-docker" {
    restart {
      attempts = 2
      interval = "30m"
      delay = "15s"
      mode = "fail"
    }

    ephemeral_disk {
      size = 1000
    }

    task "kibana" {
      driver = "docker"

      config {
        image = "kibana"
        network_mode = "host"
      }

      resources {
        cpu    = 500
        memory = 500
      }

      service {
        name = "kibana"
        tags = ["elk"]
        port = 5601
        address_mode = "driver"
        check {
          type      = "tcp"
          address_mode = "driver"
          interval  = "10s"
          timeout   = "1s"
        }
      }

      env {
        ELASTICSEARCH_URL = "http://localhost:9200"
      }
    }
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
        volumes = ["data/logstash:/config-dir"]
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
        tags = ["elk"]
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