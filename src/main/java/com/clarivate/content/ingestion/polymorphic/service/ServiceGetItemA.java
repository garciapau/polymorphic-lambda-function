package com.clarivate.content.ingestion.polymorphic.service;

import com.clarivate.content.ingestion.polymorphic.model.contract.GetItemARequest;
import com.clarivate.content.model.EntityA;
import com.clarivate.content.ingestion.ObjectMapperFactory;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseResponse;
import com.clarivate.content.ingestion.polymorphic.model.contract.GetItemAResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("ServiceGetItemA")
public class ServiceGetItemA implements BaseService {

  @Override
  public Optional<BaseResponse> execute(BaseRequest request) {
    Optional<BaseResponse> result = Optional.empty();

    if(request instanceof GetItemARequest){
      ObjectMapper mapper = ObjectMapperFactory.create();
      try {
        System.out.println("Im " + this.getClass().getName() + " and the input is " + mapper.writeValueAsString(request));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }

      EntityA entityA = new EntityA();
      entityA.setIdentifier(((GetItemARequest) request).getIdentifier());
      entityA.setName("Name of + " + ((GetItemARequest) request).getIdentifier());
      GetItemAResponse res = new GetItemAResponse();
      res.setEntityA(entityA);
      res.setEventId(request.getEventId());
      res.setMessageId(request.getMessageId());
      res.setResponseCode("EntityA retrieved successfully");
      result=Optional.of(res);
    }

    return result;
  }
}
