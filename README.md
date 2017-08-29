# Polymorphic AWS lambda example
Goal: Dynamically and cleanly provide multiple methods by the same Lambda Function

In this example, we are exemplifying how to design a Lambda Function that performs several operations, applying these 
two principles:
- Polymorphism
- Multi-injection

# Design
Lambda Function's contract is defined by `BaseRequest` and `BaseResponse` abstract classes.
Each method extends these classes to provide the specific request and response, the operation contract.
Each method is a Spring Component that implements the interface `LambdaService`, which is injected by Spring.

# How to add a new method?
For each operation:
- Create a new entry in the `RequestEventType` enumeration
- Add a new entry in the Jackson annotation for BaseRequest and BaseResponse:
    ```
    @JsonSubTypes({
        ...
        @JsonSubTypes.Type(value = <YourMethodName><Request or Response>.class, name = "<YourMethodName>"),
        ...
    })
    ```
- Implement the abstract `BaseRequest` and `BaseResponse` to provide specific request and response objects, 
call it <YourMethodName>Request and <YourMethodName>Response.
- Implement the `LambdaService` interface to define the logic of the operation. Notice that we need to only execute the 
specific request and ignore all the others:
    ```
    @Component("LambdaService<YourMethodName>")
    public class LambdaService<YourMethodName> implements LambdaService {

      @Override
      public Optional<BaseResponse> execute(BaseRequest request) {
        Optional<BaseResponse> result = Optional.empty();
        if(request instanceof <YourMethodName>Request){
            // Put your logic/calls here, 
        
        
            // Return an Optional with the method specific response object
            <YourMethodName>Response response = ...
            result=Optional.of(response);
        }
        return result;
      }
    }
    ```


# Scope
In this example:
- Only 3 types of requests (Get, Put, Delete) have been created, but it is totally open.
- Only 2 services handle the Get and Put requests. Delete is "orphan" on purpose (se To-DO section).
  

# Test
Simply run 
```
PolymorphicLambdaApplicationTest
```
 to see how 2 different requests (Get and Put) are processed
 
# TO-DO
- Solve the following issue: The direct scenario (PolymorphicTypedLambdaApplication) is not working due to a Jackson exception when the AWS Lambda 
runtime tries to parse the abstract class. It happens before our code is executed. Since it works fine in a standalone execution, it does not
look like an error of this solution. It has been work-arounded by using the RequestStreamHandler in PolymorphicLambdaApplication. 
- Provide a default request handler when the input JSON message can't be handled by existing services
- It actually uses gradle as main packaging manager but also Maven is configured, but only to build light-weight fatJar 
to be deployed as a LambdaFunction. With gradle the jar is too heavy. Looking for a Gradle equivalent to maven-assembly-plugin

