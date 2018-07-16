#!/usr/bin/env bash
nomad job run jalgoarena-submissions.nomad
nomad job run jalgoarena-ranking.nomad
nomad job run jalgoarena-ui.nomad