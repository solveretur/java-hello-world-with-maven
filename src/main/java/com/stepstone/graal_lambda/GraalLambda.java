package com.stepstone.graal_lambda;

public class GraalLambda {
    public static void main(String[] args) {
        final EchoService echoService = new EchoService();
        final String msg = "HelloWorld!!";
        final EchoResponse response = echoService.echo(msg);
        System.out.println(response);
    }
}
