package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DefaillanceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Defaillance getDefaillanceSample1() {
        return new Defaillance().id(1L).codeAgent("codeAgent1").userCreation("userCreation1").userMaj("userMaj1").numMaj(1);
    }

    public static Defaillance getDefaillanceSample2() {
        return new Defaillance().id(2L).codeAgent("codeAgent2").userCreation("userCreation2").userMaj("userMaj2").numMaj(2);
    }

    public static Defaillance getDefaillanceRandomSampleGenerator() {
        return new Defaillance()
            .id(longCount.incrementAndGet())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
