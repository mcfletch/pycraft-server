#! /bin/bash
# Create a new release for the project

RELEASE=$(cd PycraftServer && mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

VERSION_TAG="v-${RELEASE}"
TAGS=$(git tag --list "${VERSION_TAG}")
echo "Release is ${RELEASE} tag ${VERSION_TAG}"
if [ ! "${TAGS}" == "" ]; then
    echo "You have to bump the release version in pom.xml"
else
    git tag "${VERSION_TAG}"
fi
