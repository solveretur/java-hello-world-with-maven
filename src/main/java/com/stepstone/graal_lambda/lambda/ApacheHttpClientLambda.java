package com.stepstone.graal_lambda.lambda;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public final class ApacheHttpClientLambda<I, O> implements CustomRuntimeLambda<I, O> {
    private static final String AWS_LAMBDA_RUNTIME_API_HOST = "http://%s/2018-06-01";
    private static final String NEXT_EVENT_ENDPOINT = "/runtime/invocation/next";
    private static final String INIT_ERROR_ENDPOINT = "/runtime/init/error";
    private static final String EVENT_RESPONSE_ENDPOINT = "/runtime/invocation/%s/response";
    private static final String EVENT_ERROR_ENDPOINT = "/runtime/invocation/%s/response";
    private static final String AWS_LAMBDA_RUNTIME_API_ENV = "AWS_LAMBDA_RUNTIME_API";
    private static final String AWS_LAMBDA_RUNTIME_AWS_REQUEST_ID = "Lambda-Runtime-Aws-Request-Id";

    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).serializeNulls().create();
    private final HttpClient client;
    private final String awsLambdaRuntimeApiHost;

    public ApacheHttpClientLambda() {
        final String awsLambdaRuntimeApi = System.getenv(AWS_LAMBDA_RUNTIME_API_ENV);
        if (awsLambdaRuntimeApi == null) {
            throw new RuntimeException("Couldn't solve host. Sys Env AWS_LAMBDA_RUNTIME_API is not set");
        }
        this.client = HttpClientBuilder.create().build();
        this.awsLambdaRuntimeApiHost = String.format(AWS_LAMBDA_RUNTIME_API_HOST, awsLambdaRuntimeApi);
    }

    @Override
    public void run(final Function<I, O> lambdaFunction, final Class<I> iClass) {
        while (true) {
            final Event nextEvent = Optional.ofNullable(getNextEvent()).orElseThrow(() -> new RuntimeException("Next event must not be null"));
            System.out.println(nextEvent);
            final String requestId = nextEvent.getRequestId();
            final String eventJson = nextEvent.getEvent();
            final I event = readValue(eventJson, iClass);
            System.out.println(event.toString());
            try {
                final O output = lambdaFunction.apply(event);
                System.out.println(output.toString());
                sendResponse(requestId, output);
            } catch (final Exception e) {
                e.printStackTrace();
                sendRequestErrorMessage(requestId, e.getMessage());
            }
        }
    }

    private Event getNextEvent() {
        try {
            final HttpGet httpGet = new HttpGet(this.awsLambdaRuntimeApiHost + NEXT_EVENT_ENDPOINT);
            final HttpResponse httpResponse = client.execute(httpGet);
            final Header[] allHeaders = httpResponse.getAllHeaders();
            final String requestId = Arrays.stream(allHeaders)
                    .filter(h -> AWS_LAMBDA_RUNTIME_AWS_REQUEST_ID.equals(h.getName()))
                    .findFirst()
                    .map(Header::getValue)
                    .orElseThrow(() -> new RuntimeException("Couldn't get requestId"));
            final HttpEntity entity = httpResponse.getEntity();
            System.out.println(entity);
            final String event = EntityUtils.toString(entity, "UTF-8");
            httpGet.releaseConnection();
            return new Event(requestId, event);
        } catch (Exception e) {
            e.printStackTrace();
            sendInitErrorMessage(e.getMessage());
        }
        return null;
    }

    private HttpResponse sendResponse(final String requestId, final O response) {
        final String message = writeValue(response);
        final String endpoint = String.format(EVENT_RESPONSE_ENDPOINT, requestId);
        try {
            return sendPost(endpoint, message);
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

    private HttpResponse sendPost(final String endpoint, final String json) throws IOException {
        final HttpPost httpPost = new HttpPost(this.awsLambdaRuntimeApiHost + endpoint);
        httpPost.setEntity(new StringEntity(json));
        final HttpResponse response = client.execute(httpPost);
        httpPost.releaseConnection();
        return response;
    }

    @SuppressWarnings("unchecked")
    private I readValue(final String value, final Class<I> clazz) {
        if (String.class.equals(clazz)) {
            return (I) value;
        }
        return gson.fromJson(value, clazz);
    }

    private String writeValue(final Object o) {
        return gson.toJson(o);
    }
}
