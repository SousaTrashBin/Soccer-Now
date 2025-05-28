package pt.ul.fc.css.soccernow.util;

import java.util.List;

public class TournamentSearchParams {
    private String name;
    private List<String> teams;

    private Integer numGamesClosed;
    private Integer minGamesClosed;
    private Integer maxGamesClosed;

    private Integer numGamesOpened;
    private Integer minGamesOpened;
    private Integer maxGamesOpened;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public Integer getNumGamesClosed() {
        return numGamesClosed;
    }

    public void setNumGamesClosed(Integer numGamesClosed) {
        this.numGamesClosed = numGamesClosed;
    }

    public Integer getMinGamesClosed() {
        return minGamesClosed;
    }

    public void setMinGamesClosed(Integer minGamesClosed) {
        this.minGamesClosed = minGamesClosed;
    }

    public Integer getMaxGamesClosed() {
        return maxGamesClosed;
    }

    public void setMaxGamesClosed(Integer maxGamesClosed) {
        this.maxGamesClosed = maxGamesClosed;
    }

    public Integer getNumGamesOpened() {
        return numGamesOpened;
    }

    public void setNumGamesOpened(Integer numGamesOpened) {
        this.numGamesOpened = numGamesOpened;
    }

    public Integer getMinGamesOpened() {
        return minGamesOpened;
    }

    public void setMinGamesOpened(Integer minGamesOpened) {
        this.minGamesOpened = minGamesOpened;
    }

    public Integer getMaxGamesOpened() {
        return maxGamesOpened;
    }

    public void setMaxGamesOpened(Integer maxGamesOpened) {
        this.maxGamesOpened = maxGamesOpened;
    }
}
