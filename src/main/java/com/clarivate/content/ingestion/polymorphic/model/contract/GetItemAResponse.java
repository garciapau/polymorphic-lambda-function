package com.clarivate.content.ingestion.polymorphic.model.contract;

import com.clarivate.content.model.EntityA;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonTypeName("GetItemA")
public class GetItemAResponse extends BaseResponse {

    @Override
    public RequestEventType getEventType() { return RequestEventType.GetItemA; }

    @JsonProperty(value="entityA", required = true)
    private EntityA entityA;

    public EntityA getEntityA() {
        return entityA;
    }

    public void setEntityA(EntityA entityA) {
        this.entityA = entityA;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
