package com.stepstone.graal_lambda.service;

public interface MathApiGatewayProxyService {
    ServiceResponse compute(final APIGatewayProxyRequestEvent event);
}
