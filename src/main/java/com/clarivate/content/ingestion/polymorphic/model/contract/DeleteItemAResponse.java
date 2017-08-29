package com.clarivate.content.ingestion.polymorphic.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonTypeName("DeleteItemA")
public class DeleteItemAResponse extends BaseResponse {

    @Override
    public RequestEventType getEventType() { return RequestEventType.DeleteItemA; }

    @JsonProperty(value = "publicationId", required = true)
    private String publicationId;

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
