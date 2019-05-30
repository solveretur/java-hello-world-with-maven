package com.stepstone.graal_lambda;

import com.stepstone.graal_lambda.lambda.ApacheHttpClientLambda;
import com.stepstone.graal_lambda.lambda.CustomRuntimeLambda;

public class GraalLambda {

    public static void main(String[] args) {
        final CustomRuntimeLambda lambda = ApacheHttpClientLambda.init();
        lambda.run(input -> "{\"response\": \"Hello world !\"}");
    }
}
