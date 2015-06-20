## Synopsis

The following is a maven project that executes standalone selenium tests on a specific login page.
The application has been designed as Page Object Model:
https://code.google.com/p/selenium/wiki/PageObjects

The automated test cases:
* user login with valid email address and valid password (positive test)
* user login with valid email address and invalid password (negative test)
* user login with valid email address and empty password (negative test)
* user login with valid email address and shorter than 6 characters password (negative test)

Following configurations have been tested:

* Ubuntu jessie/sid Linux dev 3.13.0-24-generic #46-Ubuntu SMP Thu Apr 10 19:11:08 UTC 2014 x86_64 x86_64 x86_64 GNU/Linux
** Apache Maven 2.2.1
** Java version: 1.7.0_55
** Mozilla Firefox 38.0
** Google Chrome Version 43.0.2357.125 (64-bit)

* Windows 7 Ultimate
* Apache Maven 3.3.3
* Java version: 1.7.0_71, vendor: Oracle Corporation
* Mozilla Firefox 38.0.5
* Internet Explorer 8.0

## Installation

git clone https://github.com/orailean/automation

## Tests

mvn clean test

## Logging

Log4j2 has been used. The default log directory: %APPDIR%/logs

## Contributors

Oliver Raileanu
