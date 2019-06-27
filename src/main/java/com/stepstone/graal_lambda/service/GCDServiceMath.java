package com.stepstone.graal_lambda.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Optional;

public final class GCDServiceMath implements MathApiGatewayProxyService {
    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).serializeNulls().create();


    @Override
    public RequestResponse compute(final APIGatewayProxyRequestEvent event) {
        final String body = event.getBody();
        final Data data = this.gson.fromJson(body, Data.class);
        final int i1 = Optional.ofNullable(data).map(Data::getI1).orElse(0);
        final int i2 = Optional.ofNullable(data).map(Data::getI2).orElse(0);
        int i1_val = i1;
        int i2_val = i2;
        while (i1_val != i2_val) {
            if (i1_val > i2_val) {
                i1_val = i1_val - i2_val;
            } else {
                i2_val = i2_val - i1_val;
            }
        }
        final Integer result = i1_val;
        final ServiceResponse serviceResponse = new ServiceResponse(i1, i2, result);
        final String json = gson.toJson(serviceResponse);
        return new RequestResponse(200, json, false);
    }
}
