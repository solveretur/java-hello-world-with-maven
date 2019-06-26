package com.stepstone.graal_lambda.service;

public final class ServiceResponse {
    private final Integer i1;
    private final Integer i2;
    private final Integer gcd;

    public ServiceResponse(Integer i1, Integer i2, Integer gcd) {
        this.i1 = i1;
        this.i2 = i2;
        this.gcd = gcd;
    }

    public Integer getI1() {
        return i1;
    }

    public Integer getI2() {
        return i2;
    }

    public Integer getGcd() {
        return gcd;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "i1=" + i1 +
                ", i2=" + i2 +
                ", gcd=" + gcd +
                '}';
    }
}
