package com.stepstone.graal_lambda.lambda;

final class Event {
    private final String requestId;
    private final String event;

    Event(String requestId, String event) {
        if (requestId == null) {
            throw new RuntimeException("RequestId must not be null");
        }
        this.requestId = requestId;
        this.event = event;
    }

    String getRequestId() {
        return requestId;
    }

    String getEvent() {
        return event;
    }
}
