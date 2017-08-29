package com.clarivate.content.ingestion.polymorphic.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonTypeName("GetItemB")
public class GetItemBRequest extends BaseRequest {

    @Override
    public RequestEventType getEventType() { return RequestEventType.GetItemB; }

    @JsonProperty(value = "identifier", required = true)
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
