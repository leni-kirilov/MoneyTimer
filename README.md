# MONEY TIMER

## Description

A desktop application is written in Java Swing.

It is designed to calculate money wasted in a meeting / consultation etc

Define:
- money per hour
- number of participants/consultants

The application measures the time with precision seconds.
The meetings can be PAUSED and RESUMED.

At the end of the meeting, a REPORT is visualized.

![image](https://github.com/leni-kirilov/MoneyTimer/assets/994676/48d5856c-7bfa-4901-bfbc-b3a83c6164b4)

### DEV ENV SETUP INSTRUCTIONS

1. Pull the code
2. Import as Maven project
3. Execute `mvn clean test`
4. If tests are green you're done!


### HOW TO USE

> mvn exec:java

or

> java -jar moneytimer-1.0.2-jar-with-dependencies.jar

### HOW TO BUILD EXE

#### Requirements (windows):

1. (requires WiX v3 - follow these instructions - https://stackoverflow.com/a/77459463/342003)
2. Jar with dependencies created using `mvn package`
3. Run `buildExe.bat`
4. It will create `MoneyTimer.exe` an OS binary that shouldn't require Java

#### Requirements (Linux/OSx):

TODO

### HOW TO CONTRIBUTE

1. Fork repo
2. Make changes. If you are looking for ideas, check the [ISSUES](https://github.com/leni-kirilov/MoneyTimer/issues) section in Github
3. Create a Pull Request to the origin repo
4. Congratulations!


## CONTINUOUS INTEGRATION

[![Java CI with Maven](https://github.com/leni-kirilov/MoneyTimer/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/leni-kirilov/MoneyTimer/actions/workflows/maven.yml)

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
