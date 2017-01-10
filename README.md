[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4cd549bb321f4cabb06a17ad1cb8e61c)](https://www.codacy.com/app/jacek-spolnik/JAlgoArena?utm_source=github.com&utm_medium=referral&utm_content=spolnik/JAlgoArena&utm_campaign=badger)
# JAlgoArena [![Build Status](https://travis-ci.org/spolnik/JAlgoArena.svg?branch=master)](https://travis-ci.org/spolnik/JAlgoArena)

JAlgoArena is service dedicated for hosting programming contest. There is one special thing about it - you can use it in SaaS mode as well as on-permises (self-hosted) mode - which is great alternative to portals like hackerrank and others - when you are limited to only own infrastruture or you just don't want to pay for external service or help in organizing coding contest for your developers.

JAlgoArena itself is implemented as set of microservices, based on Spring Boot and hosted (in SaaS mode) on Heroku.

Demo: [https://jalgoarena-ui.herokuapp.com/](https://jalgoarena-ui.herokuapp.com/)

- [Introduction](#introduction)
- [Components](#components)
- [E2E Tests](#e2e-tests)
- [Continuous Delivery](#continuous-delivery)
- [Infrastructure](#infrastructure)
- [Running Locally] (#running-locally)
- [Developing new Judge Agent](#developing-new-judge-agent)
- [Notes](#notes)

## Introduction

- JAlgoArena allows user to see existing problems, create account and using it submit solutions for existing problems, in one of two languages: Kotlin and Java. Every solution is limited by time and memory consumption and needs to pass all defined test cases. Problems itself are divided into three difficulty levels for each ones receiving different set of points. Additionally Kotlin language is promoted, giving you 150% of usual points in Java with same time.
- JAlgoArena conduts many parts, coming from Web UI, going through API service, which finally reaches direct parts of JAlgoArena: Judge Engine for Kotlin and Java, Authentication & Authorization Service keeping info about Users, Problems Service holding definition and meta-data about avialable problems and finally Submissions Service, where submissions are stored and ranking is calculated. Finally - all of that behind of scene is orchestrated by Eureka (discovery service) - which allows on loosely coupling between services and easy way to scale them

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/component_diagram.png)

## Components

- [JAlgoArena UI](https://github.com/spolnik/JAlgoArena-UI)
- [JAlgoArena Judge](https://github.com/spolnik/JAlgoArena-Judge)
- [JAlgoArena Problems](https://github.com/spolnik/JAlgoArena-Problems)
- [JAlgoArena Submissions (and Ranking)](https://github.com/spolnik/JAlgoArena-Submissions)
- [JAlgoArena Auth Server](https://github.com/spolnik/JAlgoArena-Auth)
- [JAlgoArena Eureka Server](https://github.com/spolnik/JAlgoArena-Eureka)
- [JAlgoArena API Gateway](https://github.com/spolnik/JAlgoArena-API)

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

- Heroku (PaaS)
- Xodus (embedded highly scalable database) - [http://jetbrains.github.io/xodus/](http://jetbrains.github.io/xodus/)
- TravisCI - [https://travis-ci.org/spolnik/JAlgoArena](https://travis-ci.org/spolnik/JAlgoArena)

## Running locally

To see detailed instructions on how to run particular components - go to below pages and look for running locally section. Below order is important if you want UI to be fully functional just after starting. Although - you can start it in any order - having some parts of functionallity not working till all parts will be started.
* [Eureka Server](https://github.com/spolnik/JAlgoArena-Eureka)
* [API Gateway](https://github.com/spolnik/JAlgoArena-API)
* [Auth Server](https://github.com/spolnik/JAlgoArena-Auth)
* [Problems Service](https://github.com/spolnik/JAlgoArena-Problems)
* [Judge Agent](https://github.com/spolnik/JAlgoArena-Judge)
* [Submissions (and Ranking) Service](https://github.com/spolnik/JAlgoArena-Submissions)
* [UI Server](https://github.com/spolnik/JAlgoArena-UI)

## Developing new Judge Agent

- If you would like to develop new judge agent supporting new programming language, or using different approach for judgement - please follow instructions in here: [how to develop new judge agent](https://github.com/spolnik/JAlgoArena/wiki/Implementing-new-Judge-Agent)

## Notes
- [Travis Builds](https://travis-ci.org/spolnik)

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/JAlgoArena_Logo.png)
