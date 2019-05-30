package com.stepstone.graal_lambda;

public final class Cat {
    private Integer id;
    private String name;
    private Boolean isHappy;

    public Cat() {
    }

    public Cat(Integer id, String name, Boolean isHappy) {
        this.id = id;
        this.name = name;
        this.isHappy = isHappy;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHappy(Boolean happy) {
        isHappy = happy;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isHappy=" + isHappy +
                '}';
    }
}
