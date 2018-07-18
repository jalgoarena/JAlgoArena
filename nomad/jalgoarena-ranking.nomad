job "jalgoarena-ranking" {
  datacenters = ["dc1"]

  update {
    max_parallel = 1
    healthy_deadline = "3m"
  }

  group "ranking-docker" {

    ephemeral_disk {
      migrate = true
      size = 1000
      sticky = true
    }

    task "jalgoarena-ranking" {
      driver = "docker"

      config {
        image = "jalgoarena/ranking:2.3.49"
        network_mode = "host"
        volumes = ["/home/jacek/jalgoarena-config/RankingStore:/app/RankingStore"]
      }

      resources {
        cpu    = 1000
        memory = 750
      }

      env {
        JAVA_OPTS = "-Xmx512m -Xms50m"
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