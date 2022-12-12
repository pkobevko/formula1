package ua.foxminded.formula1;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            File abbreviationsFile = new File("src/main/resources/abbreviations.txt");
            File startLogFile = new File("src/main/resources/start.log");
            File endLogFile = new File("src/main/resources/end.log");

            RacersRepository racersRepository = new RacersRepository(abbreviationsFile, startLogFile, endLogFile);
            List<Racer> racers = racersRepository.getRacers();
            RacersFormatter formatter = new RacersFormatter();
            System.out.println(formatter.format(racers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}