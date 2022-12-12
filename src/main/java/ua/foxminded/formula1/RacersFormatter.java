package ua.foxminded.formula1;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RacersFormatter {

    public String format(List<Racer> racers) {
        List<Racer> sortedRacers = racers.stream()
            .sorted(Comparator.comparing(Racer::getBestLapDuration))
            .collect(Collectors.toList());

        int numberOfLastRacer = sortedRacers.indexOf(sortedRacers.get(racers.size() - 1)) + 1;
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
                int racerNumber = sortedRacers.indexOf(racer) + 1;
                String spacesBeforeIndex = spaces(numOfDigits(numberOfLastRacer) - numOfDigits(racerNumber));
                String spacesAfterName = spaces(lengthOfLongestName - racer.getName().length());
                String spacesAfterTeam = spaces(lengthOfLongestTeam - racer.getTeam().length());

                String durationOfBestLap = durationAsString(racer.getBestLapDuration());

                String string = String.format("%s%d. %s%s|%s%s|%s\n",
                    spacesBeforeIndex, racerNumber, racer.getName(), spacesAfterName, racer.getTeam(), spacesAfterTeam, durationOfBestLap);

                stringBuilder.append(string);

                if (racerNumber == 15) {
                    stringBuilder.append(hyphens(string.length()) + "\n");
                }
            });

        if (stringBuilder.length() > 0) {
            int lastIndexOfString = stringBuilder.length() - 1;
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
