#!/usr/bin/env bash
docker run -d --net=host --name=consul -e CONSUL_UI_BETA=true consul agent -server -ui -bootstrap-expect=1 -bind=192.168.63.21
nomad agent -config service.hcl
