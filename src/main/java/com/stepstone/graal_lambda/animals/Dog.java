package com.stepstone.graal_lambda.animals;

public final class Dog {
    private final String voice;
    private final String catName;
    private final Boolean isAngry;

    Dog(String voice, String catName, Boolean isAngry) {
        this.voice = voice;
        this.catName = catName;
        this.isAngry = isAngry;
    }

    Dog attackCat(final Cat cat) {
        System.out.println("I attack you " + cat.getName() + " : " + this.voice);
        return new Dog(this.voice, cat.getName(), true);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "voice='" + voice + '\'' +
                ", catName='" + catName + '\'' +
                ", isAngry=" + isAngry +
                '}';
    }
}
