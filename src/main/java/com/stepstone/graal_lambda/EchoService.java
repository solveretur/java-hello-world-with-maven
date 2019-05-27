package com.stepstone.graal_lambda;

final class EchoService {

    EchoResponse echo(final String message){
        return new EchoResponse(message);
    }
}
