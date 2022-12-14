package ua.foxminded.formula1.services;

import ua.foxminded.formula1.racer.Racer;

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
    private static final char DOT = '.';
    private static final char SPACE = ' ';
    private static final char VERTICAL_BAR = '|';
    private static final String TIME_PATTERN = "mm:ss:SSS";


    public String format(List<Racer> racers) {
        List<Racer> sortedRacers = racers.stream()
            .sorted(Comparator.comparing(Racer::getBestLap))
            .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();

        createTable(stringBuilder, sortedRacers);
        deleteLastCharInStringBuilder(stringBuilder);

        return stringBuilder.toString();
    }

    private void createTable(StringBuilder stringBuilder, List<Racer> racers) {
        for (int i = 0; i < racers.size(); i++) {
            int racerNumber = i + ZERO_COMPENSATION;
            String currentLine = createRacerLine(racerNumber, racers.get(i), racers);

            if (i == NUMBER_CARS_IN_Q2) {
                stringBuilder.append(getHyphensLine(currentLine));
            }

            stringBuilder.append(currentLine);
        }
    }

    private String getHyphensLine(String currentLine) {
        return hyphens(currentLine.length() - LINE_SEPARATOR.length()) + LINE_SEPARATOR;
    }

    private String createRacerLine(int racerNumber, Racer racer, List<Racer> racers) {
        StringBuilder sb = new StringBuilder();

        sb.append(getCountPart(racerNumber, racers));
        sb.append(getNamePart(racer, racers));
        sb.append(getTeamPart(racer, racers));
        sb.append(formatDuration(racer.getBestLap()));
        sb.append(LINE_SEPARATOR);

        return sb.toString();
    }

    private String getTeamPart(Racer racer, List<Racer> racers) {
        String spacesAfterTeam = spaces(getLengthOfLongestTeam(racers) - racer.getTeam().length());
        return racer.getTeam() + spacesAfterTeam + VERTICAL_BAR;
    }

    private int getLengthOfLongestTeam(List<Racer> racers) {
        return racers.stream()
            .map(racer -> racer.getTeam().length())
            .sorted(Comparator.reverseOrder())
            .findFirst()
            .orElse(0);
    }

    private String getNamePart(Racer racer, List<Racer> racers) {
        String spacesAfterName = spaces(getLengthOfLongestName(racers) - racer.getName().length());
        return racer.getName() + spacesAfterName + VERTICAL_BAR;
    }

    private int getLengthOfLongestName(List<Racer> racers) {
        return racers.stream()
            .map(racer -> racer.getName().length())
            .sorted(Comparator.reverseOrder())
            .findFirst()
            .orElse(0);
    }

    private String getCountPart(int racerNumber, List<Racer> racers) {
        int numberOfLastRacer = getNumberOfLastRacer(racers);
        String spacesBeforeNumber = spaces(numOfDigits(numberOfLastRacer) - numOfDigits(racerNumber));

        return spacesBeforeNumber + racerNumber + DOT + SPACE;
    }

    private int getNumberOfLastRacer(List<Racer> racers) {
        return racers.indexOf(racers.get(racers.size() - ZERO_COMPENSATION)) + ZERO_COMPENSATION;
    }

    private void deleteLastCharInStringBuilder(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            int lastIndexOfString = stringBuilder.length() - 1;
            stringBuilder.deleteCharAt(lastIndexOfString);
        }
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

    private String formatDuration(Duration duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime durationAsTime = LocalTime.ofNanoOfDay(duration.toNanos());
        String parsedDuration = durationAsTime.format(formatter);
        return parsedDuration;
    }
}
