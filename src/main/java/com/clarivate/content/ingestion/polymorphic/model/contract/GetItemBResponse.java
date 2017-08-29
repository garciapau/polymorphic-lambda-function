package com.clarivate.content.ingestion.polymorphic.model.contract;

import com.clarivate.content.model.EntityB;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonTypeName("GetItemB")
public class GetItemBResponse extends BaseResponse {

    @Override
    public RequestEventType getEventType() { return RequestEventType.GetItemB; }

    @JsonProperty(value="entityB", required = true)
    private EntityB entityB;

    public EntityB getEntityB() {
        return entityB;
    }

    public void setEntityB(EntityB entityB) {
        this.entityB = entityB;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
