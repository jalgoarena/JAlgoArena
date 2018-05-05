# JAlgoArena 2 [![Build Status](https://travis-ci.org/spolnik/JAlgoArena.svg?branch=master)](https://travis-ci.org/spolnik/JAlgoArena) [![JProfiler](https://github.com/spolnik/JAlgoArena/raw/master/design/jprofiler_small.png)](http://www.ej-technologies.com/products/jprofiler/overview.html)

JAlgoArena is a programming contest platform. You can use it in SaaS mode (e.g. Heroku) as well as on-premises (self-hosted) mode - which is great alternative to portals like HackerRank and others - when you are limited to only own infrastructure or you just don't want to pay for external service or help in organizing coding contest for your developers.

JAlgoArena itself is implemented as set of microservices, based on Spring Boot and hosted (in SaaS mode) on Heroku.

- [Introduction](#introduction)
- [Components](#components)
- [Kanban Board](#kanban-board)
- [E2E Tests](#e2e-tests)
- [Continuous Delivery](#continuous-delivery)
- [Infrastructure](#infrastructure)
- [Running Locally](#running-locally)
- [Developing new Judge Agent](#developing-new-judge-agent)
- [Notes](#notes)

## Introduction

- JAlgoArena allows user to see existing problems, create account and using it submit solutions for existing problems, in one of two languages: Kotlin and Java. Every solution is limited by time and memory consumption and needs to pass all defined test cases. Problems itself are divided into three difficulty levels for each ones receiving different set of points. Additionally Kotlin language is promoted, giving you 150% of usual points in Java with same time.
- JAlgoArena conducts many parts, coming from Web UI, going through API service, which finally reaches direct parts of JAlgoArena: Judge Engine for Kotlin and Java, Authentication & Authorization Service keeping info about Users, Problems Service holding definition and meta-data about avialable problems and finally Submissions Service, where submissions are stored and ranking is calculated. Finally - all of that behind of scene is orchestrated by Eureka (discovery service) - which allows on loosely coupling between services and easy way to scale them

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/component_diagram.png)

## Components

- [JAlgoArena UI](https://github.com/spolnik/JAlgoArena-UI)
- [JAlgoArena Eureka Server](https://github.com/spolnik/JAlgoArena-Eureka)
- [JAlgoArena API Gateway](https://github.com/spolnik/JAlgoArena-API)
- [JAlgoArena Auth Server](https://github.com/spolnik/JAlgoArena-Auth)
- [JAlgoArena Submissions](https://github.com/spolnik/JAlgoArena-Submissions)
- [JAlgoArena Ranking](https://github.com/spolnik/JAlgoArena-Ranking)
- [JAlgoArena Queue](https://github.com/spolnik/JAlgoArena-Queue)
- [JAlgoArena Judge](https://github.com/spolnik/JAlgoArena-Judge)
- [JAlgoArena Events](https://github.com/spolnik/JAlgoArena-Events)

# Kanban Board

JAlgoArena [kanban board](https://github.com/spolnik/JAlgoArena/projects/1) showing planned features and their development progress.

![Kanban Board](https://github.com/spolnik/JAlgoArena/blob/master/design/JAlgoArena-Project.png)

| State | Priority | Description |
| ------------- | ------------- | ------------- |
| Ideas | Unset | Not yet decided if they will be done |
| Backlog | Low | To be done |
| TODO | High | Will be done for the next releases |
| In Progress | High | Features in progress |
| Done | High | Features implemented and released |

## E2E Tests

- end to end tests written in spock
- it runs against deployed applications (Heroku)
- it covers 2 cases, 1st for Java and 2nd for Kotlin

## Continuous Delivery

- initially, developer push his changes to GitHub
- in next stage, GitHub notifies Travis CI about changes
- Travis CI runs whole continuous integration flow, running compilation, tests and generating reports
- coverage report is sent to Codecov
- application is deployed into Heroku machine

![Continuous Delivery](https://github.com/spolnik/JAlgoArena/raw/master/design/continuous_delivery.png)

## Infrastructure

- Xodus (embedded highly scalable database) - [Xodus home page](http://jetbrains.github.io/xodus/)
- [Xodus Entities Browser](https://github.com/JetBrains/xodus-entity-browser)
- [Apache Kafka](https://kafka.apache.org)
- [Netflix Eureka](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)
- [Netflix Zuul 1.0](https://github.com/Netflix/zuul/wiki/How-it-Works)
- TravisCI - [https://travis-ci.org/spolnik/JAlgoArena](https://travis-ci.org/spolnik/JAlgoArena)
- Heroku (PaaS) or any other hosting infra

## Running locally

To see detailed instructions on how to run particular components - go to below pages and look for running locally section. Below order is important if you want UI to be fully functional just after starting. Although - you can start it in any order - having some parts of functionallity not working till all parts will be started.
* Kafka & Zookeeper - **TBD**
* [Eureka Server](https://github.com/spolnik/JAlgoArena-Eureka)
* [API Gateway](https://github.com/spolnik/JAlgoArena-API)
* [Auth Server](https://github.com/spolnik/JAlgoArena-Auth)
* [Queue Service](https://github.com/spolnik/JAlgoArena-Queue)
* [Events Service](https://github.com/spolnik/JAlgoArena-Events)
* [Judge Agent](https://github.com/spolnik/JAlgoArena-Judge)
* [Submissions Service](https://github.com/spolnik/JAlgoArena-Submissions)
* [Ranking Service](https://github.com/spolnik/JAlgoArena-Ranking)
* [UI Server](https://github.com/spolnik/JAlgoArena-UI)

## Developing new Judge Agent

- If you would like to develop new judge agent supporting new programming language, or using different approach for judgement - please follow instructions in here: [how to develop new judge agent](https://github.com/spolnik/JAlgoArena/wiki/Implementing-new-Judge-Agent)

## Notes
- [Travis Builds](https://travis-ci.org/spolnik)

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/JAlgoArena_Logo.png)
