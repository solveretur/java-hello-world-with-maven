package com.stepstone.graal_lambda.lambda;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public final class ApacheHttpClientLambda implements CustomRuntimeLambda {
    private static final String AWS_LAMBDA_RUNTIME_API_HOST = "http://%s/2018-06-01";
    private static final String NEXT_EVENT_ENDPOINT = "/runtime/invocation/next";
    private static final String INIT_ERROR_ENDPOINT = "/runtime/init/error";
    private static final String EVENT_RESPONSE_ENDPOINT = "/runtime/invocation/%s/response";
    private static final String EVENT_ERROR_ENDPOINT = "/runtime/invocation/%s/response";
    private static final String AWS_LAMBDA_RUNTIME_API_ENV = "AWS_LAMBDA_RUNTIME_API";
    private static final String AWS_LAMBDA_RUNTIME_AWS_REQUEST_ID = "Lambda-Runtime-Aws-Request-Id";

    private final HttpClient client;
    private final String awsLambdaRuntimeApiHost;

    private ApacheHttpClientLambda(HttpClient client, String awsLambdaRuntimeApiHost) {
        this.client = client;
        this.awsLambdaRuntimeApiHost = awsLambdaRuntimeApiHost;
    }

    public static ApacheHttpClientLambda init() {
        final String awsLambdaRuntimeApi = System.getenv(AWS_LAMBDA_RUNTIME_API_ENV);
        if (awsLambdaRuntimeApi == null) {
            throw new RuntimeException("Couldn't solve sys env $AWS_LAMBDA_RUNTIME_API");
        }
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final String awsLambdaRunTimeApiHost = String.format(AWS_LAMBDA_RUNTIME_API_HOST, awsLambdaRuntimeApi);
        return new ApacheHttpClientLambda(httpClient, awsLambdaRunTimeApiHost);
    }

    @Override
    public String run(Function<String, String> lambdaFunction) {
        while (true) {
            final Event nextEvent = Optional.ofNullable(getNextEvent()).orElseThrow(() -> new RuntimeException("Next event must not be null"));
            final String requestId = nextEvent.getRequestId();
            final String event = nextEvent.getEvent();
            try {
                final String output = lambdaFunction.apply(event);
                sendResponse(requestId, output);
            } catch (final Exception e) {
                e.printStackTrace();
                sendRequestErrorMessage(requestId, e.getMessage());
            }
        }
    }

    private Event getNextEvent() {
        try {
            final HttpResponse httpResponse = sendGet(NEXT_EVENT_ENDPOINT);
            final Header[] allHeaders = httpResponse.getAllHeaders();
            final String requestId = Arrays.stream(allHeaders)
                    .filter(h -> AWS_LAMBDA_RUNTIME_AWS_REQUEST_ID.equals(h.getName()))
                    .findFirst()
                    .map(Header::getValue)
                    .orElseThrow(() -> new RuntimeException("Couldn't get requestId"));
            final HttpEntity entity = httpResponse.getEntity();
            final String event = EntityUtils.toString(entity, "UTF-8");
            return new Event(requestId, event);
        } catch (Exception e) {
            e.printStackTrace();
            sendInitErrorMessage(e.getMessage());
        }
        return null;
    }

    private HttpResponse sendResponse(final String requestId, final String response) {
        final String endpoint = String.format(EVENT_RESPONSE_ENDPOINT, requestId);
        try {
            return sendPost(endpoint, response);
        } catch (IOException e) {
            e.printStackTrace();
            sendRequestErrorMessage(requestId, e.getMessage());
        }
        return null;
    }

    private void sendRequestErrorMessage(final String requestId, final String message) {
        final String endpoint = String.format(EVENT_ERROR_ENDPOINT, requestId);
        try {
            sendPost(endpoint, message);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void sendInitErrorMessage(final String message) {
        try {
            sendPost(INIT_ERROR_ENDPOINT, message);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private HttpResponse sendGet(final String endpoint) throws IOException {
        final HttpGet httpGet = new HttpGet(this.awsLambdaRuntimeApiHost + endpoint);
        final HttpResponse response = client.execute(httpGet);
        httpGet.releaseConnection();
        return response;
    }

    private HttpResponse sendPost(final String endpoint, final String json) throws IOException {
        final HttpPost httpPost = new HttpPost(this.awsLambdaRuntimeApiHost + endpoint);
        httpPost.setEntity(new StringEntity(json));
        final HttpResponse response = client.execute(httpPost);
        httpPost.releaseConnection();
        return response;
    }


}
