package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ResultatSolvableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ResultatSolvable getResultatSolvableSample1() {
        return new ResultatSolvable()
            .id(1L)
            .codeEtatSolvabilite("codeEtatSolvabilite1")
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static ResultatSolvable getResultatSolvableSample2() {
        return new ResultatSolvable()
            .id(2L)
            .codeEtatSolvabilite("codeEtatSolvabilite2")
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static ResultatSolvable getResultatSolvableRandomSampleGenerator() {
        return new ResultatSolvable()
            .id(longCount.incrementAndGet())
            .codeEtatSolvabilite(UUID.randomUUID().toString())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
