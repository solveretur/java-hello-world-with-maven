package com.stepstone.graal_lambda.animals;

public final class Cat {
    private final Integer id;
    private final String name;
    private final Boolean isHappy;

    public Cat(Integer id, String name, Boolean isHappy) {
        this.id = id;
        this.name = name;
        this.isHappy = isHappy;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getHappy() {
        return isHappy;
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
