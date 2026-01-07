package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DemarcheTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Demarche getDemarcheSample1() {
        return new Demarche()
            .id(1L)
            .numDemarche("numDemarche1")
            .codeOrigine("codeOrigine1")
            .codeStatut("codeStatut1")
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static Demarche getDemarcheSample2() {
        return new Demarche()
            .id(2L)
            .numDemarche("numDemarche2")
            .codeOrigine("codeOrigine2")
            .codeStatut("codeStatut2")
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static Demarche getDemarcheRandomSampleGenerator() {
        return new Demarche()
            .id(longCount.incrementAndGet())
            .numDemarche(UUID.randomUUID().toString())
            .codeOrigine(UUID.randomUUID().toString())
            .codeStatut(UUID.randomUUID().toString())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
