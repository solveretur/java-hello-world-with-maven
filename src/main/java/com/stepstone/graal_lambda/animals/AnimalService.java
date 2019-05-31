package com.stepstone.graal_lambda.animals;

public final class AnimalService {

    public Dog meetCat(final Cat cat) {
        final Dog dog = new Dog("Attack !!!", null, false);
        System.out.println("First time: " + dog.toString());
        System.out.println("I see the cat...");
        final Dog afterAtack = dog.attackCat(cat);
        System.out.println(afterAtack);
        return afterAtack;
    }
}
