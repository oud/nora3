package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CreancierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Creancier getCreancierSample1() {
        return new Creancier()
            .id(1L)
            .nir("nir1")
            .cleNir(1)
            .numAllocCristal(1)
            .numPersonneCristal(1)
            .codeOrganismeCristal("codeOrganismeCristal1")
            .codeSituationFamiliale("codeSituationFamiliale1")
            .codeNationalite("codeNationalite1")
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static Creancier getCreancierSample2() {
        return new Creancier()
            .id(2L)
            .nir("nir2")
            .cleNir(2)
            .numAllocCristal(2)
            .numPersonneCristal(2)
            .codeOrganismeCristal("codeOrganismeCristal2")
            .codeSituationFamiliale("codeSituationFamiliale2")
            .codeNationalite("codeNationalite2")
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static Creancier getCreancierRandomSampleGenerator() {
        return new Creancier()
            .id(longCount.incrementAndGet())
            .nir(UUID.randomUUID().toString())
            .cleNir(intCount.incrementAndGet())
            .numAllocCristal(intCount.incrementAndGet())
            .numPersonneCristal(intCount.incrementAndGet())
            .codeOrganismeCristal(UUID.randomUUID().toString())
            .codeSituationFamiliale(UUID.randomUUID().toString())
            .codeNationalite(UUID.randomUUID().toString())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
