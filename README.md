# MONEY TIMER

## Description
A desktop application written in Java Swing.

It is designed to calculate money wasted in a meeting / consultation etc

Define:
- money per hour
- number of participants/consultants

The application measures the time with precision seconds.
The meetings are able to be PAUSED and RESUMED.

At the end of the meeting a REPORT is visualized.

### DEV ENV SETUP INSTRUCTIONS

1. Pull the code
2. Import as Maven project
3. Execute `mvn clean test`
4. If tests are green you're done!


### HOW TO USE

> java -cp moneytimer-1.0.1.jar bg.kirilov.timer.Main

### HOW TO CONTRIBUTE

1. Fork repo
2. Make changes. If you are looking for ideas, check the [ISSUES](https://github.com/leni-kirilov/MoneyTimer/issues) section in Github
3. Create a Pull Request to the origin repo
4. Congratulations!


## CONTINUOUS INTEGRATION

[![Build Status](https://travis-ci.org/leni-kirilov/MoneyTimer.svg?branch=master)](https://travis-ci.org/leni-kirilov/MoneyTimer)
[![Build Status](https://drone.io/github.com/leni-kirilov/MoneyTimer/status.png)](https://drone.io/github.com/leni-kirilov/MoneyTimer/latest)
[![Coverage Status](https://img.shields.io/coveralls/leni-kirilov/MoneyTimer.svg)](https://coveralls.io/r/leni-kirilov/MoneyTimer?branch=master)

### Licence
Copyright 2016 Leni Kirilov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.