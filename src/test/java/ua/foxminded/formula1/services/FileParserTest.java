package ua.foxminded.formula1.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.foxminded.formula1.racer.Racer;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FileParserTest {

    FileParser fileParser = new FileParser();
    File abbreviations3 = new File("src/test/resources/abbreviations3.txt");
    File startLog3 = new File("src/test/resources/start3.log");
    File endLog3 = new File("src/test/resources/end3.log");
    File emptyFile = new File("src/test/resources/empty-file.txt");

    @Test
    void parseAbbreviationsFile_shouldThrowIllegalArgumentException_whenPassingNull() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                fileParser.parseAbbreviationsFile(null);
            });

        String expectedMessage = "File cannot be null";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void parseAbbreviationsFile_shouldThrowIllegalArgumentException_whenFileDoesNotExist() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                fileParser.parseAbbreviationsFile(new File("test"));
            });

        String expectedMessage = "File does not exist";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void parseAbbreviationsFile_shouldThrowIllegalArgumentException_whenFileIsEmpty() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                fileParser.parseAbbreviationsFile(emptyFile);
            });

        String expectedMessage = "File cannot be empty";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void parseLogFiles_shouldThrowIllegalArgumentException_whenPassingNull() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                fileParser.parseLogFiles(null, null);
            });

        String expectedMessage = "File cannot be null";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void parseLogFiles_shouldThrowIllegalArgumentException_whenFileDoesNotExist() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                fileParser.parseLogFiles(new File("test"), new File("test1"));
            });

        String expectedMessage = "File does not exist";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void parseLogFiles_shouldThrowIllegalArgumentException_whenFileIsEmpty() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
            () -> {
                fileParser.parseLogFiles(emptyFile, emptyFile);
            });

        String expectedMessage = "File cannot be empty";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void parseAbbreviationsFile_shouldReturnEmptyList_whenSomethingWrongWithParsingFile() {
        File wrongFile = new File("src/test/resources/");

        List<Racer> expected = new ArrayList<>();
        List<Racer> actual = fileParser.parseAbbreviationsFile(wrongFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parseAbbreviationsFile_shouldReturnCorrectList_whenExample1() {
        Racer racer1 = new Racer("DRR", "Daniel Ricciardo", "RED BULL RACING TAG HEUER");
        Racer racer2 = new Racer("PGS", "Pierre Gasly", "SCUDERIA TORO ROSSO HONDA");
        Racer racer3 = new Racer("LSW", "Lance Stroll", "WILLIAMS MERCEDES");
        List<Racer> expected = List.of(racer1, racer2, racer3);

        List<Racer> actual = fileParser.parseAbbreviationsFile(abbreviations3);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parseLogFiles_shouldReturnEmptyList_whenSomethingWrongWithParsingFiles() {
        File wrongFile = new File("src/test/resources/");

        Map<String, Duration> expected = new HashMap<>();
        Map<String, Duration> actual = fileParser.parseLogFiles(wrongFile, endLog3);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parseLogFiles_shouldReturnCorrectList_whenExample1() {
        Map<String, Duration> expected = Map.of(
            "DRR", Duration.parse("PT1M12.013S"),
            "PGS", Duration.parse("PT1M12.941S"),
            "LSW", Duration.parse("PT1M13.323S"));

        Map<String, Duration> actual = fileParser.parseLogFiles(startLog3, endLog3);

        Assertions.assertEquals(expected, actual);
    }
}