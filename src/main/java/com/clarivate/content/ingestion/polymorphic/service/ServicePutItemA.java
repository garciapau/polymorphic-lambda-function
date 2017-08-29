package com.clarivate.content.ingestion.polymorphic.service;

import com.clarivate.content.ingestion.ObjectMapperFactory;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseResponse;
import com.clarivate.content.ingestion.polymorphic.model.contract.PutItemAResponse;
import com.clarivate.content.ingestion.polymorphic.model.contract.PutItemARequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("ServicePutItemA")
public class ServicePutItemA implements BaseService {

  @Override
  public Optional<BaseResponse> execute(BaseRequest request) {
    Optional<BaseResponse> result = Optional.empty();

    if(request instanceof PutItemARequest){
      ObjectMapper mapper = ObjectMapperFactory.create();
      try {
        System.out.println("Im " + this.getClass().getName() + " and the input is " + mapper.writeValueAsString(request));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }

      PutItemAResponse res = new PutItemAResponse();
      res.setPublicationId(((PutItemARequest) request).getEntityA().getIdentifier());
      res.setEventId(request.getEventId());
      res.setMessageId(request.getMessageId());
      res.setResponseCode("EntityA created successfully");
      result=Optional.of(res);
    }

    return result;
  }
}
