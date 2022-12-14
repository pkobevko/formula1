package ua.foxminded.formula1.racer;

import java.time.Duration;

public class Racer {

    private final String abbreviation;
    private final String name;
    private final String team;
    private Duration bestLap;

    public Racer(String abbreviation, String name, String team) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.team = team;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public Duration getBestLap() {
        return bestLap;
    }

    public void setBestLap(Duration bestLap) {
        this.bestLap = bestLap;
    }
}
