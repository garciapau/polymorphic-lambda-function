package com.clarivate.content.ingestion.polymorphic.model.contract;

public enum RequestEventType {
    /**
     */
    GetItemA,
    /**
     */
    GetItemB,
    /**
     */
    PutItemA,
    /**
     * Not implemented on purpose
     */
    UpdateItemA,
    /**
     * Not implemented on purpose
     */
    DeleteItemA;

    public int getValue() {
        return this.ordinal();
    }

    public static RequestEventType forValue(int value) {
        return values()[value];
    }
}
