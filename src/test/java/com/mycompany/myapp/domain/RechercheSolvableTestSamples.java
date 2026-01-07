package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RechercheSolvableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static RechercheSolvable getRechercheSolvableSample1() {
        return new RechercheSolvable().id(1L).codeAgent("codeAgent1").userCreation("userCreation1").userMaj("userMaj1").numMaj(1);
    }

    public static RechercheSolvable getRechercheSolvableSample2() {
        return new RechercheSolvable().id(2L).codeAgent("codeAgent2").userCreation("userCreation2").userMaj("userMaj2").numMaj(2);
    }

    public static RechercheSolvable getRechercheSolvableRandomSampleGenerator() {
        return new RechercheSolvable()
            .id(longCount.incrementAndGet())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
