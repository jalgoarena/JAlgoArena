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

## Continuous Delivery

- initially, developer push his changes to GitHub
- in next stage, GitHub notifies Travis CI about changes
- Travis CI runs whole continuous integration flow, running compilation, tests and generating reports
- coverage report is sent to Codecov
- zip package is saved to GitHub releases per every component repository

![Continuous Delivery](https://github.com/spolnik/JAlgoArena/raw/master/design/continuous_delivery.png)

![Component Diagram](https://github.com/spolnik/JAlgoArena/raw/master/design/JAlgoArena_Logo.png)
