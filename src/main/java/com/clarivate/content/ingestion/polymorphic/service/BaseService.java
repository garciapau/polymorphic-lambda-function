package com.clarivate.content.ingestion.polymorphic.service;

import com.clarivate.content.ingestion.polymorphic.model.contract.BaseRequest;
import com.clarivate.content.ingestion.polymorphic.model.contract.BaseResponse;

import java.util.Optional;

public interface BaseService {

  Optional<BaseResponse> execute(BaseRequest request);


/*
    default Optional<BaseResponse> execute(BaseRequest request){
    // TODO not working yet...
    ObjectMapper mapper = ObjectMapperFactory.create();
    try {
      System.out.println("Im " + this.getClass().getDescription() + " and no one is attending this request " + mapper.writeValueAsString(request));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
*/
}
