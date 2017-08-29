#!/bin/bash
echo 1. packaging the lambda funtion...
mvn package
echo Done. 
echo 2. Creating lambda function in AWS...
aws lambda create-function --function-name polymorphic-lambda-dev-snapshot \
    --runtime java8 --handler com.clarivate.content.ingestion.polymorphiclambda.PolymorphicLambdaApplication \
    --role arn:aws:iam::509786517216:role/workflow-platform-lambda \
    --zip-file fileb://target/polymorphic-lambda-1.0.0-jar-with-dependencies.jar --description polymorphic-lambda \
    --environment Variables="{WF_ENV=dev,WF_SUBENV=snapshot}" \
    --memory-size 384 \
    --timeout 120

echo Done. 
