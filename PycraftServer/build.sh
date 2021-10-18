#! /bin/bash 

mvn -e package
cp target/PycraftServer-1.0.1.jar ../../plugins/
