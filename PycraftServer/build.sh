#! /bin/bash 
set -e

mvn -e package
cp target/PycraftServer-1.0.8.jar ../../plugins/
