package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DossierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Dossier getDossierSample1() {
        return new Dossier()
            .id(1L)
            .numDossierNor("numDossierNor1")
            .numDossierGaia("numDossierGaia1")
            .codeOrganisme("codeOrganisme1")
            .codeAgent("codeAgent1")
            .userCreation("userCreation1")
            .userMaj("userMaj1")
            .numMaj(1);
    }

    public static Dossier getDossierSample2() {
        return new Dossier()
            .id(2L)
            .numDossierNor("numDossierNor2")
            .numDossierGaia("numDossierGaia2")
            .codeOrganisme("codeOrganisme2")
            .codeAgent("codeAgent2")
            .userCreation("userCreation2")
            .userMaj("userMaj2")
            .numMaj(2);
    }

    public static Dossier getDossierRandomSampleGenerator() {
        return new Dossier()
            .id(longCount.incrementAndGet())
            .numDossierNor(UUID.randomUUID().toString())
            .numDossierGaia(UUID.randomUUID().toString())
            .codeOrganisme(UUID.randomUUID().toString())
            .codeAgent(UUID.randomUUID().toString())
            .userCreation(UUID.randomUUID().toString())
            .userMaj(UUID.randomUUID().toString())
            .numMaj(intCount.incrementAndGet());
    }
}
