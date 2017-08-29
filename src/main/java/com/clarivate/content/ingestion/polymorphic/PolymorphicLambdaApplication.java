package com.clarivate.content.ingestion.polymorphic;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.clarivate.content.ingestion.ObjectMapperFactory;
import com.clarivate.content.ingestion.polymorphic.model.contract.*;
import com.clarivate.content.ingestion.polymorphic.service.BaseService;
import com.clarivate.content.model.EntityA;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = {"com.clarivate.content.ingestion.polymorphic.service"})
public class PolymorphicLambdaApplication implements RequestStreamHandler {
	ObjectMapper mapper = ObjectMapperFactory.create();

	@Autowired
	private Set<BaseService> baseServices;

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		BaseRequest req = null;
		try {
			req = mapper.readValue(inputStream, BaseRequest.class);
			context.getLogger().log("Request is " + mapper.writeValueAsString(req));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BaseResponse resp = new PolymorphicLambdaApplication().process(applicationContext, req);
		mapper.writeValue(outputStream, resp);
	}

	public BaseResponse process(ApplicationContext applicationContext, BaseRequest input) {
/*
		List<Optional<BaseResponse>> responses = baseServices
				.stream()
				.map(lambdaService -> lambdaService.execute(input))
				.filter(result -> result.isPresent())
				.collect(Collectors.toList());
*/
		List<Optional<BaseResponse>> responses = applicationContext.getBeansOfType(BaseService.class).values()
				.stream()
				.map(lambdaService -> lambdaService.execute(input))
				.filter(result -> result.isPresent())
				.collect(Collectors.toList());
		try {
			System.out.println("The response is --> " + mapper.writeValueAsString(responses.get(0).get()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responses.get(0).get();
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		EntityA entityA = new EntityA();
		entityA.setIdentifier("000001");
		entityA.setName("Title A");

		PutItemARequest input = new PutItemARequest();
		input.setEntityA(entityA);
		input.setEventId("event0001");
		input.setMessageId("msg00001");

		new PolymorphicLambdaApplication().process(applicationContext, input);
	}
}
