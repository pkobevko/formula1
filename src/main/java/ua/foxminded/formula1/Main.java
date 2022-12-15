package ua.foxminded.formula1;

import ua.foxminded.formula1.racer.Racer;
import ua.foxminded.formula1.services.RacersFormatter;
import ua.foxminded.formula1.services.RacersResult;

import java.io.File;
import java.util.List;

public class Main {

    private static File abbreviations = new File("src/main/resources/abbreviations`.txt");
    private static File startLog = new File("src/main/resources/start.log");
    private static File endLog = new File("src/main/resources/end.log");

    public static void main(String[] args) {

        RacersResult racersResult = new RacersResult();
        List<Racer> racers = racersResult.createListOfRacers(abbreviations, startLog, endLog);
        RacersFormatter formatter = new RacersFormatter();
        System.out.println(formatter.format(racers));
    }
}