package ua.foxminded.formula1.services;

import ua.foxminded.formula1.racer.Racer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileParser {

    public static final String SEPARATOR = "_";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    public static final int ABBREVIATION_LENGTH = 3;

    public List<Racer> parseAbbreviationsFile(File file) throws IOException {
        checkFile(file);

        return Files.lines(file.toPath())
            .map(line -> line.split(SEPARATOR))
            .map(s -> new Racer(s[0], s[1], s[2]))
            .collect(Collectors.toList());
    }

    public Map<String, Duration> parseLogFiles(File startLog, File endLog) throws IOException {
        checkFile(startLog);
        checkFile(endLog);

        Map<String, LocalDateTime> start = parseLogFile(startLog);
        Map<String, LocalDateTime> end = parseLogFile(endLog);

        Map<String, Duration> result = new HashMap<>();
        start.forEach((key, value) -> result.put(key, Duration.between(value, end.get(key))));

        return result;
    }

    private Map<String, LocalDateTime> parseLogFile(File log) throws IOException {
        checkFile(log);

        return Files.lines(log.toPath())
            .collect(Collectors.toMap(key -> key.substring(0, ABBREVIATION_LENGTH),
                value -> LocalDateTime.parse(value.substring(ABBREVIATION_LENGTH), FORMATTER)));
    }

    private void checkFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }

        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        if (file.length() == 0) {
            throw new IllegalArgumentException("File cannot be empty");
        }
    }
}
