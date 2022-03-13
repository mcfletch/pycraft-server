#! /bin/bash 

mvn -e package
cp target/PycraftServer-1.0.3.jar ../../plugins/
