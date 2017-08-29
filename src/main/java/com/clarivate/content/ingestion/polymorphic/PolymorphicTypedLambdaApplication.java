package com.clarivate.content.ingestion.polymorphic;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.clarivate.content.ingestion.ObjectMapperFactory;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseResponse;
import com.clarivate.content.ingestion.polymorphic.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = {"com.clarivate.content.ingestion.polymorphiclambda.service"})
public class PolymorphicTypedLambdaApplication implements RequestHandler<BaseRequest, BaseResponse> {

	@Autowired
	private Set<BaseService> baseServices;

	@Override
	public BaseResponse handleRequest(BaseRequest input, Context context) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		String response = "";
		ObjectMapper mapper = ObjectMapperFactory.create();
		try {
			context.getLogger().log("Request is " + mapper.writeValueAsString(input));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BaseResponse resp = new PolymorphicTypedLambdaApplication().process(applicationContext, input);
		try {
			context.getLogger().log("Response is " + mapper.writeValueAsString(resp));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resp;
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
			ObjectMapper mapper = ObjectMapperFactory.create();
			System.out.println("The response is --> " + mapper.writeValueAsString(responses.get(0).get()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responses.get(0).get();
	}

}
