package com.stepstone.graal_lambda.lambda;

import java.util.function.Function;

public interface CustomRuntimeLambda {
    String run(final Function<String, String> lambdaFunction);
}
