package ua.foxminded.formula1.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.foxminded.formula1.racer.Racer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class RacersFormatterTest {

    RacersFormatter formatter = new RacersFormatter();
    RacersResult racersResult = new RacersResult();
    File abbreviations19 = new File("src/test/resources/abbreviations19.txt");
    File startLog19 = new File("src/test/resources/start19.log");
    File endLog19 = new File("src/test/resources/end19.log");
    File abbreviations3 = new File("src/test/resources/abbreviations3.txt");
    File startLog3 = new File("src/test/resources/start3.log");
    File endLog3 = new File("src/test/resources/end3.log");

    @Test
    void format_shouldThrowIllegalArgumentException_whenPassingNull() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                formatter.format(null);
            });

        String expectedMessage = "You cannot pass null to this function";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void format_shouldThrowIllegalArgumentException_whenPassingEmptyList() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                formatter.format(new ArrayList<>());
            });

        String expectedMessage = "List cannot be empty";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void format_shouldReturnCorrectlyFormattedString_whenExample1() {
        String expected = """
             1. Sebastian Vettel |FERRARI                  |01:04:415
             2. Daniel Ricciardo |RED BULL RACING TAG HEUER|01:12:013
             3. Valtteri Bottas  |MERCEDES                 |01:12:434
             4. Lewis Hamilton   |MERCEDES                 |01:12:460
             5. Stoffel Vandoorne|MCLAREN RENAULT          |01:12:463
             6. Kimi Raikkonen   |FERRARI                  |01:12:639
             7. Fernando Alonso  |MCLAREN RENAULT          |01:12:657
             8. Sergey Sirotkin  |WILLIAMS MERCEDES        |01:12:706
             9. Charles Leclerc  |SAUBER FERRARI           |01:12:829
            10. Sergio Perez     |FORCE INDIA MERCEDES     |01:12:848
            11. Romain Grosjean  |HAAS FERRARI             |01:12:930
            12. Pierre Gasly     |SCUDERIA TORO ROSSO HONDA|01:12:941
            13. Carlos Sainz     |RENAULT                  |01:12:950
            14. Esteban Ocon     |FORCE INDIA MERCEDES     |01:13:028
            15. Nico Hulkenberg  |RENAULT                  |01:13:065
            ---------------------------------------------------------
            16. Brendon Hartley  |SCUDERIA TORO ROSSO HONDA|01:13:179
            17. Marcus Ericsson  |SAUBER FERRARI           |01:13:265
            18. Lance Stroll     |WILLIAMS MERCEDES        |01:13:323
            19. Kevin Magnussen  |HAAS FERRARI             |01:13:393""";

        List<Racer> racers = racersResult.createListOfRacers(abbreviations19, startLog19, endLog19);
        String actual = formatter.format(racers);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void format_shouldReturnCorrectlyFormattedString_whenExample2() {
        String expected = """
            1. Daniel Ricciardo|RED BULL RACING TAG HEUER|01:12:013
            2. Pierre Gasly    |SCUDERIA TORO ROSSO HONDA|01:12:941
            3. Lance Stroll    |WILLIAMS MERCEDES        |01:13:323""";


        List<Racer> racers = racersResult.createListOfRacers(abbreviations3, startLog3, endLog3);
        String actual = formatter.format(racers);

        Assertions.assertEquals(expected, actual);
    }


}