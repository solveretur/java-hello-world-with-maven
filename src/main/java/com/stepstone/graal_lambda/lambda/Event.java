package com.stepstone.graal_lambda.lambda;

final class Event {
    private final String requestId;
    private final String event;

    public Event(String requestId, String event) {
        if (requestId == null) {
            throw new RuntimeException("RequestId must not be null");
        }
        this.requestId = requestId;
        this.event = event;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getEvent() {
        return event;
    }
}
