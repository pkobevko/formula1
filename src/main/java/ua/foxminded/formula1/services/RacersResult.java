package ua.foxminded.formula1.services;

import ua.foxminded.formula1.racer.Racer;

import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RacersResult {

    public List<Racer> createListOfRacers(File abbreviations, File startLog, File endLog) {
        FileParser fileParser = new FileParser();

        List<Racer> racers = fileParser.parseAbbreviationsFile(abbreviations);
        Map<String, Duration> lapTimes = fileParser.parseLogFiles(startLog, endLog);

        if (racers.isEmpty() || lapTimes.isEmpty()) {
            return Collections.emptyList();
        }

        addLapTimesToRacers(racers, lapTimes);
        return racers;
    }

    private void addLapTimesToRacers(List<Racer> racers, Map<String, Duration> lapTimes) {
        racers.forEach(racer -> {
            racer.setBestLap(lapTimes.get(racer.getAbbreviation()));
        });
    }
}
