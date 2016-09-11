## Synopsis

The following is a maven project that executes standalone selenium tests on a specific quiz page.
The application has been designed as Page Object Model:
https://code.google.com/p/selenium/wiki/PageObjects

The automated test cases:
* user login with valid email address and valid password (positive test)
* asserts for display of the quiz result page
* asserts for the javascript errors
* timeouts for > 30 seconds

Following configurations have been tested:

##### Linux ubuntu 3.19.0-25-generic #26~14.04.1-Ubuntu SMP Fri Jul 24 21:16:20 UTC 2015 x86_64 x86_64 x86_64 GNU/Linux
* Apache Maven 3.0.5
* Java version: 1.8.0_101
* Google Chrome Version 53.0.2785.101 (64-bit)

## Installation

GIT clone:
```
git clone https://github.com/orailean/sosw.git
```

## Tests

Maven:
```
mvn clean test
```

## Logging

Log4j2 has been used. The default log directory: %APPDIR%/logs

## Contributors

Oliver Raileanu
