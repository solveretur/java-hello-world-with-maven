package com.stepstone.graal_lambda.service;

public final class Data {
    private final Integer i1;
    private final Integer i2;

    public Data(Integer i1, Integer i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    public Integer getI1() {
        return i1;
    }

    public Integer getI2() {
        return i2;
    }

    @Override
    public String toString() {
        return "Data{" +
                "i1=" + i1 +
                ", i2=" + i2 +
                '}';
    }
}
