package ua.foxminded.formula1;

import ua.foxminded.formula1.racer.Racer;
import ua.foxminded.formula1.services.RacersFormatter;
import ua.foxminded.formula1.services.RacersResult;

import java.io.File;
import java.util.List;

public class Main {

    private static String ABBREVIATIONS_PATH = "src/main/resources/abbreviations.txt";
    private static String START_LOG_PATH = "src/main/resources/start.log";
    private static String END_LOG_PATH = "src/main/resources/end.log";

    public static void main(String[] args) {
        File abbreviations = new File(ABBREVIATIONS_PATH);
        File startLog = new File(START_LOG_PATH);
        File endLog = new File(END_LOG_PATH);

        RacersResult racersResult = new RacersResult();
        List<Racer> racers = racersResult.createListOfRacers(abbreviations, startLog, endLog);
        RacersFormatter formatter = new RacersFormatter();
        System.out.println(formatter.format(racers));
    }
}