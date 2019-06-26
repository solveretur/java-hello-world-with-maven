package com.stepstone.graal_lambda.lambda;

import java.util.function.Function;

public interface CustomRuntimeLambda<I, O> {
    void run(final Function<I, O> lambdaFunction,final Class<I> clazz);
}
