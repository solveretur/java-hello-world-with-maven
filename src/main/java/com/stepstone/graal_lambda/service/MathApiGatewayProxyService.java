package com.stepstone.graal_lambda.service;

public interface MathApiGatewayProxyService {
    RequestResponse compute(final APIGatewayProxyRequestEvent event);
}
