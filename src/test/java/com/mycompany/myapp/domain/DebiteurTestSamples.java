package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DebiteurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Debiteur getDebiteurSample1() {
        return new Debiteur()
            .id(1L)
            .nir("nir1")
            .cleNir(1)
            .numAllocCristal(1)
            .codeOrganismeCristal("codeOrganismeCristal1")
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static Debiteur getDebiteurSample2() {
        return new Debiteur()
            .id(2L)
            .nir("nir2")
            .cleNir(2)
            .numAllocCristal(2)
            .codeOrganismeCristal("codeOrganismeCristal2")
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static Debiteur getDebiteurRandomSampleGenerator() {
        return new Debiteur()
            .id(longCount.incrementAndGet())
            .nir(UUID.randomUUID().toString())
            .cleNir(intCount.incrementAndGet())
            .numAllocCristal(intCount.incrementAndGet())
            .codeOrganismeCristal(UUID.randomUUID().toString())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
