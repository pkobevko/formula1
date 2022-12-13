package ua.foxminded.formula1;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RacersFormatter {

    private static final String LINE_SEPARATOR = "\n";
    private static final int ZERO_COMPENSATION = 1;
    private static final int NUMBER_CARS_IN_Q2 = 15;

    public String format(List<Racer> racers) {
        List<Racer> sortedRacers = racers.stream()
            .sorted(Comparator.comparing(Racer::getBestLapDuration))
            .collect(Collectors.toList());

        int numberOfLastRacer = sortedRacers.indexOf(sortedRacers.get(racers.size() - ZERO_COMPENSATION)) + ZERO_COMPENSATION;
        int lengthOfLongestName = sortedRacers.stream()
            .map(racer -> racer.getName().length())
            .sorted(Comparator.reverseOrder())
            .findFirst()
            .orElse(0);

        int lengthOfLongestTeam = sortedRacers.stream()
            .map(racer -> racer.getTeam().length())
            .sorted(Comparator.reverseOrder())
            .findFirst()
            .orElse(0);

        StringBuilder stringBuilder = new StringBuilder();

        sortedRacers.stream()
            .forEach(racer -> {
                int racerNumber = sortedRacers.indexOf(racer) + ZERO_COMPENSATION;
                String spacesBeforeIndex = spaces(numOfDigits(numberOfLastRacer) - numOfDigits(racerNumber));
                String spacesAfterName = spaces(lengthOfLongestName - racer.getName().length());
                String spacesAfterTeam = spaces(lengthOfLongestTeam - racer.getTeam().length());

                String durationOfBestLap = durationAsString(racer.getBestLapDuration());

                String currentString = String.format("%s%d. %s%s|%s%s|%s",
                    spacesBeforeIndex, racerNumber, racer.getName(), spacesAfterName, racer.getTeam(), spacesAfterTeam, durationOfBestLap);

                stringBuilder.append(currentString + LINE_SEPARATOR);

                if (racerNumber == NUMBER_CARS_IN_Q2) {
                    stringBuilder.append(hyphens(currentString.length()) + LINE_SEPARATOR);
                }
            });

        if (stringBuilder.length() > 0) {
            int lastIndexOfString = stringBuilder.length() - ZERO_COMPENSATION;
            stringBuilder.deleteCharAt(lastIndexOfString);
        }

        return stringBuilder.toString();
    }

    private String spaces(int count) {
        return " ".repeat(count);
    }

    private String hyphens(int count) {
        return "-".repeat(count);
    }

    private int numOfDigits(int num) {
        if (num == 0) {
            return 1;
        }

        int count = 0;
        while (num != 0) {
            num /= 10;
            count++;
        }
        return count;
    }

    private String durationAsString(Duration duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        LocalTime durationAsTime = LocalTime.ofNanoOfDay(duration.toNanos());
        String parsedDuration = durationAsTime.format(formatter);

        String resultWithoutHours = parsedDuration.substring("00:".length());
        return resultWithoutHours;
    }
}
