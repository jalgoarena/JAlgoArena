#!/usr/bin/env bash
nomad job run jalgoarena-auth.nomad
nomad job run jalgoarena-queue.nomad
nomad job run jalgoarena-events.nomad
nomad job run jalgoarena-judge.nomad