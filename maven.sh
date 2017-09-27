#!/usr/bin/env bash
mvn clean test site
firefox ./target/site/allure-maven-plugin.html