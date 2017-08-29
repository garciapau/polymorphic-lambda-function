package com.clarivate.content.ingestion.polymorphic;

import com.clarivate.content.ingestion.polymorphic.PolymorphicLambdaApplication;
import com.clarivate.content.model.EntityA;
import com.clarivate.content.ingestion.ObjectMapperFactory;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseResponse;
import com.clarivate.content.ingestion.polymorphic.model.contract.GetItemARequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.GetItemBRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.PutItemARequest;
import com.clarivate.content.ingestion.polymorphic.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PolymorphicLambdaApplication.class}, loader = AnnotationConfigContextLoader.class)
public class PolymorphicLambdaApplicationTest {
    ObjectMapper mapper = ObjectMapperFactory.create();

    @Autowired
    private Set<BaseService> baseServices;

    @BeforeClass
    public static void before() {
    }

    @Test
    public void test_putItemA_should_return_putItemAResponse() {
        EntityA entityA = new EntityA();
        entityA.setIdentifier("000001");
        entityA.setName("Title A");

        PutItemARequest input = new PutItemARequest();
        input.setEntityA(entityA);
        input.setEventId("event0001");
        input.setMessageId("msg00001");

        List<Optional<BaseResponse>> responses = baseServices
                .stream()
                .map(lambdaService -> lambdaService.execute(input))
                .filter(result -> result.isPresent())
                .collect(Collectors.toList());
        assertThat(responses.get(0).isPresent(), is(true));
        assertThat(responses.get(0).get().getClass().getName(), is("com.clarivate.content.ingestion.polymorphic.model.contract.PutItemAResponse"));
        try {
            System.out.println("The response is " + mapper.writeValueAsString(responses.get(0).get()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test_getItemA_should_return_getItemAResponse() {
        GetItemARequest input = new GetItemARequest();
        input.setIdentifier("000002");
        input.setEventId("event0002");
        input.setMessageId("msg00002");

        List<Optional<BaseResponse>> responses = baseServices
                .stream()
                .map(lambdaService -> lambdaService.execute(input))
                .filter(result -> result.isPresent())
                .collect(Collectors.toList());
        assertThat(responses.get(0).isPresent(), is(true));
        assertThat(responses.get(0).get().getClass().getName(), is("com.clarivate.content.ingestion.polymorphic.model.contract.GetItemAResponse"));
        try {
            System.out.println("The response is " + mapper.writeValueAsString(responses.get(0).get()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test_getItemB_should_return_getItemBResponse() {
        GetItemBRequest input = new GetItemBRequest();
        input.setIdentifier("000003");
        input.setEventId("event0003");
        input.setMessageId("msg00003");

        List<Optional<BaseResponse>> responses = baseServices
                .stream()
                .map(lambdaService -> lambdaService.execute(input))
                .filter(result -> result.isPresent())
                .collect(Collectors.toList());
        assertThat(responses.get(0).isPresent(), is(true));
        assertThat(responses.get(0).get().getClass().getName(), is("com.clarivate.content.ingestion.polymorphic.model.contract.GetItemBResponse"));
        try {
            System.out.println("The response is " + mapper.writeValueAsString(responses.get(0).get()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}