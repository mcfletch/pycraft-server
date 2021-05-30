#! /bin/bash 

mvn -e package
cp target/PycraftServer-1.0.0.jar ../../scratch-world/plugins/
cp target/PycraftServer-1.0.0.jar ../../moosh-world/plugins/
cp target/PycraftServer-1.0.0.jar ../../slime-world/plugins/
