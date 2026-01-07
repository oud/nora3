package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EnfantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Enfant getEnfantSample1() {
        return new Enfant()
            .id(1L)
            .nir("nir1")
            .cleNir(1)
            .numPersonneGaia(1)
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static Enfant getEnfantSample2() {
        return new Enfant()
            .id(2L)
            .nir("nir2")
            .cleNir(2)
            .numPersonneGaia(2)
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static Enfant getEnfantRandomSampleGenerator() {
        return new Enfant()
            .id(longCount.incrementAndGet())
            .nir(UUID.randomUUID().toString())
            .cleNir(intCount.incrementAndGet())
            .numPersonneGaia(intCount.incrementAndGet())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
