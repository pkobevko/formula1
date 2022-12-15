package ua.foxminded.formula1.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.foxminded.formula1.racer.Racer;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

class RacersResultTest {

    RacersResult racersResult = new RacersResult();
    File abbreviations3 = new File("src/test/resources/abbreviations3.txt");
    File startLog3 = new File("src/test/resources/start3.log");
    File endLog3 = new File("src/test/resources/end3.log");

    @Test
    void createListOfRacers_shouldReturnCorrectListWithRacers_whenExample1() {
        Racer racer1 = new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER");
        Racer racer2 = new Racer("PGS", "Pierre Gasly", "SCUDERIA TORO ROSSO HONDA");
        Racer racer3 = new Racer("LSW", "Lance Stroll", "WILLIAMS MERCEDES");
        racer1.setBestLap(Duration.parse("PT1M12.013S"));
        racer2.setBestLap(Duration.parse("PT1M12.941S"));
        racer3.setBestLap(Duration.parse("PT1M13.323S"));

        List<Racer> expected = List.of(racer1, racer2, racer3);
        List<Racer> actual = racersResult.createListOfRacers(abbreviations3, startLog3, endLog3);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createListOfRacers_shouldReturnEmptyListWithRacers_whenOneOfFilesIsWrong() {
        File wrongFile = new File("src/test/resources/");

        List<Racer> expected = new ArrayList<>();
        List<Racer> actual = racersResult.createListOfRacers(wrongFile, startLog3, endLog3);

        Assertions.assertEquals(expected, actual);
    }

}