package ua.foxminded.formula1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RacersRepository {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    public static final String SEPARATOR = "_";
    public static final int ABBREVIATION_LENGTH = 3;

    private final File abbreviationsFile;
    private final File startLogFile;
    private final File endLogFile;

    public RacersRepository(File abbreviationsFile, File startLogFile, File endLogFile) {
        validateFiles(abbreviationsFile, startLogFile, endLogFile);
        this.abbreviationsFile = abbreviationsFile;
        this.startLogFile = startLogFile;
        this.endLogFile = endLogFile;
    }

    public List<Racer> getRacers() throws IOException {
        Map<String, LocalDateTime> startLogMap = parseTime(this.startLogFile);
        Map<String, LocalDateTime> endLogMap = parseTime(this.endLogFile);

        return Files.lines(this.abbreviationsFile.toPath())
            .map(line -> line.split(SEPARATOR))
            .map(e -> new Racer(e[1], e[2], Duration.between(startLogMap.get(e[0]), endLogMap.get(e[0]))))
            .collect(Collectors.toList());
    }

    private Map<String, LocalDateTime> parseTime(File log) throws IOException {
        return Files.lines(log.toPath())
            .collect(Collectors.toMap(s -> s.substring(0, ABBREVIATION_LENGTH), s -> LocalDateTime.parse(s.substring(ABBREVIATION_LENGTH), FORMATTER)));
    }

    private void validateFiles(File abbreviations, File startLog, File endLog) {
        if (abbreviations == null || startLog == null || endLog == null ||
            !abbreviations.isFile() || !startLog.isFile() || !endLog.isFile()) {

            throw new IllegalArgumentException("The files are incorrect");
        }
    }
}
