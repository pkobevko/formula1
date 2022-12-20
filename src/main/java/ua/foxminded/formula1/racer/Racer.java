package ua.foxminded.formula1.racer;

import java.time.Duration;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Racer racer = (Racer) o;

        if (!Objects.equals(abbreviation, racer.abbreviation)) return false;
        if (!Objects.equals(name, racer.name)) return false;
        if (!Objects.equals(team, racer.team)) return false;
        return Objects.equals(bestLap, racer.bestLap);
    }

    @Override
    public int hashCode() {
        int result = abbreviation != null ? abbreviation.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        result = 31 * result + (bestLap != null ? bestLap.hashCode() : 0);
        return result;
    }
}
