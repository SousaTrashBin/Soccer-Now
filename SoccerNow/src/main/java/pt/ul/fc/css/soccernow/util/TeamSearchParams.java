package pt.ul.fc.css.soccernow.util;

import java.util.ArrayList;
import java.util.List;

public class TeamSearchParams {
    private String name;

    private Integer numPlayers;
    private Integer minPlayers;
    private Integer maxPlayers;

    private Integer numVictories;
    private Integer minVictories;
    private Integer maxVictories;

    private Integer numDraws;
    private Integer minDraws;
    private Integer maxDraws;

    private Integer numLosses;
    private Integer minLosses;
    private Integer maxLosses;

    private Integer numAchievements;
    private Integer minAchievements;
    private Integer maxAchievements;

    private List<FutsalPositionEnum> missingPositions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Integer getNumVictories() {
        return numVictories;
    }

    public void setNumVictories(Integer numVictories) {
        this.numVictories = numVictories;
    }

    public Integer getMinVictories() {
        return minVictories;
    }

    public void setMinVictories(Integer minVictories) {
        this.minVictories = minVictories;
    }

    public Integer getMaxVictories() {
        return maxVictories;
    }

    public void setMaxVictories(Integer maxVictories) {
        this.maxVictories = maxVictories;
    }

    public Integer getNumDraws() {
        return numDraws;
    }

    public void setNumDraws(Integer numDraws) {
        this.numDraws = numDraws;
    }

    public Integer getMinDraws() {
        return minDraws;
    }

    public void setMinDraws(Integer minDraws) {
        this.minDraws = minDraws;
    }

    public Integer getMaxDraws() {
        return maxDraws;
    }

    public void setMaxDraws(Integer maxDraws) {
        this.maxDraws = maxDraws;
    }

    public Integer getNumLosses() {
        return numLosses;
    }

    public void setNumLosses(Integer numLosses) {
        this.numLosses = numLosses;
    }

    public Integer getMinLosses() {
        return minLosses;
    }

    public void setMinLosses(Integer minLosses) {
        this.minLosses = minLosses;
    }

    public Integer getMaxLosses() {
        return maxLosses;
    }

    public void setMaxLosses(Integer maxLosses) {
        this.maxLosses = maxLosses;
    }

    public Integer getNumAchievements() {
        return numAchievements;
    }

    public void setNumAchievements(Integer numAchievements) {
        this.numAchievements = numAchievements;
    }

    public Integer getMinAchievements() {
        return minAchievements;
    }

    public void setMinAchievements(Integer minAchievements) {
        this.minAchievements = minAchievements;
    }

    public Integer getMaxAchievements() {
        return maxAchievements;
    }

    public void setMaxAchievements(Integer maxAchievements) {
        this.maxAchievements = maxAchievements;
    }

    public List<FutsalPositionEnum> getMissingPositions() {
        return missingPositions;
    }

    public void setMissingPositions(List<FutsalPositionEnum> missingPositions) {
        this.missingPositions = missingPositions;
    }

}
