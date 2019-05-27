package com.stepstone.graal_lambda;

import java.time.LocalDateTime;

final class EchoResponse {
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    EchoResponse(final String message){
        this.message=message;
    }

    String getMessage(){
        return message;
    }

    LocalDateTime getTimestamp(){
        return timestamp;
    }

    @Override
    public String toString() {
        return "EchoResponse{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

