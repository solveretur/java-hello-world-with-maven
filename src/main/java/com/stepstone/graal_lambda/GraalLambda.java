package com.stepstone.graal_lambda;

import com.stepstone.graal_lambda.lambda.ApacheHttpClientLambda;
import com.stepstone.graal_lambda.lambda.CustomRuntimeLambda;
import com.stepstone.graal_lambda.service.APIGatewayProxyRequestEvent;
import com.stepstone.graal_lambda.service.GCDServiceMath;
import com.stepstone.graal_lambda.service.MathApiGatewayProxyService;
import com.stepstone.graal_lambda.service.RequestResponse;

public class GraalLambda {

    public static void main(String[] args) {
        final MathApiGatewayProxyService mathService = new GCDServiceMath();
        final CustomRuntimeLambda<APIGatewayProxyRequestEvent, RequestResponse> lambda = new ApacheHttpClientLambda<>();
        lambda.run(mathService::compute, APIGatewayProxyRequestEvent.class);
    }
}
