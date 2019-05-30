package com.stepstone.graal_lambda.lambda;

import java.util.function.Function;

public interface CustomRuntimeLambda<I, O> {
    O run(final Function<I, O> lambdaFunction, Class<I> clazz);
}
