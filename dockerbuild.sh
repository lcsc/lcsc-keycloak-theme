#!/bin/sh

docker run --rm -v $PWD:/app -w/app maven:3.9.1 mvn -f lcsc-provider/pom.xml clean package
