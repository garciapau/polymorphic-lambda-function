package com.clarivate.content.ingestion.polymorphic.service;

import com.clarivate.content.ingestion.polymorphic.model.contract.BaseRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseResponse;
import com.clarivate.content.ingestion.polymorphic.model.contract.GetItemBRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.GetItemBResponse;
import com.clarivate.content.model.EntityB;
import com.clarivate.content.ingestion.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("ServiceGetItemB")
public class ServiceGetItemB implements BaseService {

  @Override
  public Optional<BaseResponse> execute(BaseRequest request) {
    Optional<BaseResponse> result = Optional.empty();

    if(request instanceof GetItemBRequest){
      ObjectMapper mapper = ObjectMapperFactory.create();
      try {
        System.out.println("Im " + this.getClass().getName() + " and the input is " + mapper.writeValueAsString(request));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }

      GetItemBResponse res = new GetItemBResponse();
      EntityB entityB = new EntityB();
      entityB.setIdentifier(((GetItemBRequest) request).getIdentifier());
      entityB.setDescription("Description of + " + ((GetItemBRequest) request).getIdentifier());
      res.setEntityB(entityB);
      res.setEventId(request.getEventId());
      res.setMessageId(request.getMessageId());
      res.setResponseCode("EntityB retrieved successfully");
      result=Optional.of(res);
    }

    return result;
  }
}
