package ua.foxminded.formula1;

import java.time.Duration;

public class Racer {

    private final String name;
    private final String team;
    private final Duration bestLapDuration;

    public Racer(String name, String team, Duration bestLapDuration) {
        this.name = name;
        this.team = team;
        this.bestLapDuration = bestLapDuration;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public Duration getBestLapDuration() {
        return bestLapDuration;
    }
}
