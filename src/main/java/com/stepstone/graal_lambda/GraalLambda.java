package com.stepstone.graal_lambda;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;


public class GraalLambda {
    private static final String NEXT_EVENT_ENDPOINT = "http://%s/2018-06-01/runtime/invocation/next";
    private static final String AWS_LAMBDA_RUNTIME_AWS_REQUEST_ID = "Lambda-Runtime-Aws-Request-Id";
    private static final String EVENT_RESPONSE = "http://%s/2018-06-01/runtime/invocation/%s/response";

    public static void main(String[] args) {
        while (true) {
            final String awsLambdaRuntimeApi = System.getenv("AWS_LAMBDA_RUNTIME_API");
            if (awsLambdaRuntimeApi == null) {
                throw new RuntimeException("Couldn't solve host:");
            }
            final String nextEventEndpoint = String.format(NEXT_EVENT_ENDPOINT, awsLambdaRuntimeApi);
            System.out.println("Listening endpoint on: " + nextEventEndpoint);
            final HttpClient client = HttpClientBuilder.create().build();
            final HttpGet nextEventRequest = new HttpGet(nextEventEndpoint);
            final EchoService echoService = new EchoService();
            try {
                final HttpResponse response = client.execute(nextEventRequest);
                System.out.println("Retrieved response: " + response.toString());
                final Header[] allHeaders = response.getAllHeaders();
                final String requestId = Arrays.stream(allHeaders)
                        .filter(h -> AWS_LAMBDA_RUNTIME_AWS_REQUEST_ID.equals(h.getName()))
                        .findFirst()
                        .map(Header::getValue)
                        .orElseThrow(() -> new RuntimeException("Couldn't get requestId"));
                System.out.println("RequestId: " + requestId);
                final String lambdaResponseUrl = String.format(EVENT_RESPONSE, awsLambdaRuntimeApi, requestId);
                System.out.println("Lambda response url: " + lambdaResponseUrl);
                final String lambdaResponseBody = echoService.echo("Hello world !").toString();
                System.out.println("Lambda response body: " + lambdaResponseBody);
                final HttpPost httpPost = new HttpPost(lambdaResponseUrl);
                httpPost.setEntity(new StringEntity(lambdaResponseBody));
                final HttpResponse lambdaResponseResponse = client.execute(httpPost);
                System.out.println("Retrieved response response: " + lambdaResponseResponse.toString());
            } catch (final IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Couldn't get next event");
            }
        }
    }
}
