# JAlgoArena [![Build Status](https://travis-ci.org/spolnik/JAlgoArena.svg?branch=master)](https://travis-ci.org/spolnik/JAlgoArena) [![JProfiler](https://github.com/spolnik/JAlgoArena/raw/master/design/jprofiler_small.png)](http://www.ej-technologies.com/products/jprofiler/overview.html)

JAlgoArena is a programming contest platform. You can host it on any infrastructure which runs Java & Node.JS.

> JAlgoArena is a great alternative to portals like HackerRank and similar - when you are limited to only own infrastructure or you just don't want to pay for external service or help in organizing coding contest for your developers. 

## Introduction

JAlgoArena allows user to see existing problems, create account and using it submit solutions for existing problems. 

* Every solution is limited by time and memory consumption and needs to pass all defined test cases
* Problems itself are divided into three difficulty levels for each ones receiving different set of points

## UI

* JAlgoArena is created using Responsive UI based on bootstrap framework. 
* Internally all is done with usage of React components and bootstrap styles.

See more details on [JAlgoArena Docs](https://jalgoarena.github.io/docs/) page.

## Architecture

JAlgoArena conducts many parts, which can be divided to:
* Node.JS hosted Web UI
* Traefik as Edge Service
* set of backend microservices using Service Discovery
* Apache Kafka used for messaging internally in the backend
* Elastic Stack for capturing distributed logs

![Component Diagram](design/component_diagram.png)

1. Publish submission to Kafka.
1. Save new submission (JAlgoArena-Submissions) & start judge process (JAlgoArena-Judge)
   1. Request submissions refresh via WebSocket subscriptions (JAlgoArena-Submissions)
1. Publish submission result
1. Store submission and ranking result (the second only if submission is accepted)
1. Request ranking & submissions refresh via WebSocket subscriptions

## Components

- [JAlgoArena UI](https://github.com/spolnik/JAlgoArena-UI)
- [JAlgoArena Auth Server](https://github.com/spolnik/JAlgoArena-Auth)
- [JAlgoArena Queue](https://github.com/spolnik/JAlgoArena-Queue)
- [JAlgoArena Submissions](https://github.com/spolnik/JAlgoArena-Submissions)
- [JAlgoArena Judge](https://github.com/spolnik/JAlgoArena-Judge)
- [JAlgoArena Ranking](https://github.com/spolnik/JAlgoArena-Ranking)
- [JAlgoArena Events](https://github.com/spolnik/JAlgoArena-Events)

## Kanban Board

JAlgoArena [kanban board](https://github.com/spolnik/JAlgoArena/projects/1) showing planned features and their development progress.

![Kanban Board](design/JAlgoArena-Project.png)

| State | Priority | Description |
| ------------- | ------------- | ------------- |
| Backlog | Low | To be done |
| TODO | High | Will be done for the next releases |
| In Progress | High | Features in progress |
| Done | High | Features implemented and released |

## E2E Tests

- end to end tests written in spock
- it runs against locally running JAlgoArena

## Continuous Delivery

- initially, developer push his changes to GitHub
- in next stage, GitHub notifies Travis CI about changes
- Travis CI runs whole continuous integration flow, running compilation, tests and generating reports
- coverage report is sent to Codecov
- zip package is saved to GitHub releases per every component repository

![Continuous Delivery](https://github.com/spolnik/JAlgoArena/raw/master/design/continuous_delivery.png)

## Infrastructure

- [Consul](https://consul.io/) cluster used for service discovery
- [Traefik](https://traefik.io/) edge service put in front of REST services
- Xodus (embedded highly scalable database) - [Xodus home page](http://jetbrains.github.io/xodus/)
- [Xodus Entities Browser](https://github.com/JetBrains/xodus-entity-browser)
- [Apache Kafka](https://kafka.apache.org)
- TravisCI - [https://travis-ci.org/spolnik/JAlgoArena](https://travis-ci.org/spolnik/JAlgoArena)
- [ELK](https://www.elastic.co)

## Developing new Judge Agent

- If you would like to develop new judge agent supporting new programming language, or using different approach for judgement - please follow instructions in here: [how to develop new judge agent](https://github.com/spolnik/JAlgoArena/wiki/Implementing-new-Judge-Agent)

## Notes
- [Travis Builds](https://travis-ci.org/spolnik)

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/JAlgoArena_Logo.png)
