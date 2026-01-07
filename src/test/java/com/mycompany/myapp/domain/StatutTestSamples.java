package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class StatutTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Statut getStatutSample1() {
        return new Statut()
            .id(1L)
            .codeStatut("codeStatut1")
            .motifStatut("motifStatut1")
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static Statut getStatutSample2() {
        return new Statut()
            .id(2L)
            .codeStatut("codeStatut2")
            .motifStatut("motifStatut2")
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static Statut getStatutRandomSampleGenerator() {
        return new Statut()
            .id(longCount.incrementAndGet())
            .codeStatut(UUID.randomUUID().toString())
            .motifStatut(UUID.randomUUID().toString())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
