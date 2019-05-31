package com.stepstone.graal_lambda;

import com.stepstone.graal_lambda.animals.AnimalService;
import com.stepstone.graal_lambda.animals.Cat;
import com.stepstone.graal_lambda.animals.Dog;
import com.stepstone.graal_lambda.lambda.ApacheHttpClientLambda;
import com.stepstone.graal_lambda.lambda.CustomRuntimeLambda;

public class GraalLambda {

    public static void main(String[] args) {
        final AnimalService as = new AnimalService();
        final CustomRuntimeLambda<Cat, Dog> lambda = new ApacheHttpClientLambda<>();
        lambda.run(as::meetCat, Cat.class);
    }
}
