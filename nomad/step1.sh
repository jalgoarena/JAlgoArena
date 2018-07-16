#!/usr/bin/env bash
nomad job run jalgoarena-elk.nomad
nomad job run jalgoarena-logstash.nomad
nomad job run jalgoarena-kafka.nomad
nomad job run jalgoarena-traefik.nomad