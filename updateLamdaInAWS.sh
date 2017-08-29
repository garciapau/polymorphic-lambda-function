#!/bin/bash
echo 0. retrieving workflow definitions from a central git or S3 repo?... TBD

echo 1. NOT Installing workflow tasks
cd ../WorkflowTasks
mvn install
echo Done.

echo 2. packaging the lambda funtion...
cd ../WorkflowEngine
mvn package
echo Done. 

echo 2. Updating AWS...
aws lambda update-function-code --function-name polymorphic-lambda-dev-snapshot --zip-file fileb://target/polymorphic-lambda-1.0.0-jar-with-dependencies.jar
echo Done.
