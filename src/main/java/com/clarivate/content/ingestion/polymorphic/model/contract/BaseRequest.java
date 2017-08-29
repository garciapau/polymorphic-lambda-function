package com.clarivate.content.ingestion.polymorphic.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetItemARequest.class, name = "GetItemA"),
        @JsonSubTypes.Type(value = GetItemARequest.class, name = "GetItemB"),
        @JsonSubTypes.Type(value = PutItemARequest.class, name = "PutItemA"),
        @JsonSubTypes.Type(value = DeleteItemARequest.class, name = "DeleteItemA"),
})
public abstract class BaseRequest {

    public abstract RequestEventType getEventType();

    @JsonProperty(value="messageId", required = true)
    private String messageId;

    @JsonProperty(value="eventId", required = true)
    private String eventId;

    @JsonProperty(value="eventType", required = true)
    private RequestEventType eventType = RequestEventType.values()[0];

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
