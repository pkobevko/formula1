package ua.foxminded.formula1.services;

import ua.foxminded.formula1.racer.Racer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileParser {

    public static final String SEPARATOR = "_";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    public static final int ABBREVIATION_LENGTH = 3;

    public List<Racer> parseAbbreviationsFile(File file) {
        checkFile(file);

        List<Racer> racers;

        try {
            racers = Files.lines(file.toPath()).map(line -> line.split(SEPARATOR))
                .map(s -> new Racer(s[0], s[1], s[2]))
                .collect(Collectors.toList());
        } catch (IOException e) {
            racers = new ArrayList<>();
        }
        return racers;
    }

    public Map<String, Duration> parseLogFiles(File startLog, File endLog) {

        Map<String, LocalDateTime> start = parseLogFile(startLog);
        Map<String, LocalDateTime> end = parseLogFile(endLog);

        if (start.isEmpty() || end.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Duration> result = new HashMap<>();
        start.forEach((key, value) -> result.put(key, Duration.between(value, end.get(key))));

        return result;
    }

    private Map<String, LocalDateTime> parseLogFile(File log) {
        checkFile(log);

        Map<String, LocalDateTime> logTimes;

        try {
            logTimes = Files.lines(log.toPath())
                .collect(Collectors.toMap(key -> key.substring(0, ABBREVIATION_LENGTH),
                    value -> LocalDateTime.parse(value.substring(ABBREVIATION_LENGTH), FORMATTER)));
        } catch (IOException e) {
            logTimes = new HashMap<>();
        }
        return logTimes;
    }

    private void checkFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }

        if (file.length() == 0) {
            throw new IllegalArgumentException("File cannot be empty");
        }
    }
}
