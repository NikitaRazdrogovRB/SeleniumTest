#!/usr/bin/env bash
mvn clean test site
chrome ./target/site/allure-maven-plugin.html