package pt.ul.fc.css.soccernow.util;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerSearchParams {
    @Min(1)
    private Integer size;

    @NotNull
    private RedCardOrder redCardComparatorOrder = RedCardOrder.ASC;
    private String name;
    private List<FutsalPositionEnum> preferredPositions = new ArrayList<>();

    private Integer numReceivedCards;
    private Integer minReceivedCards;
    private Integer maxReceivedCards;

    private Integer numScoredGoals;
    private Integer minScoredGoals;
    private Integer maxScoredGoals;

    private Integer numGames;
    private Integer minGames;
    private Integer maxGames;

    public RedCardOrder getRedCardComparatorOrder() {
        return redCardComparatorOrder;
    }

    public void setRedCardComparatorOrder(RedCardOrder redCardComparatorOrder) {
        this.redCardComparatorOrder = redCardComparatorOrder;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumReceivedCards() {
        return numReceivedCards;
    }

    public void setNumReceivedCards(Integer numReceivedCards) {
        this.numReceivedCards = numReceivedCards;
    }

    public Integer getMinReceivedCards() {
        return minReceivedCards;
    }

    public void setMinReceivedCards(Integer minReceivedCards) {
        this.minReceivedCards = minReceivedCards;
    }

    public Integer getMaxReceivedCards() {
        return maxReceivedCards;
    }

    public void setMaxReceivedCards(Integer maxReceivedCards) {
        this.maxReceivedCards = maxReceivedCards;
    }

    public Integer getNumScoredGoals() {
        return numScoredGoals;
    }

    public void setNumScoredGoals(Integer numScoredGoals) {
        this.numScoredGoals = numScoredGoals;
    }

    public Integer getMinScoredGoals() {
        return minScoredGoals;
    }

    public void setMinScoredGoals(Integer minScoredGoals) {
        this.minScoredGoals = minScoredGoals;
    }

    public Integer getMaxScoredGoals() {
        return maxScoredGoals;
    }

    public void setMaxScoredGoals(Integer maxScoredGoals) {
        this.maxScoredGoals = maxScoredGoals;
    }

    public Integer getNumGames() {
        return numGames;
    }

    public void setNumGames(Integer numGames) {
        this.numGames = numGames;
    }

    public Integer getMinGames() {
        return minGames;
    }

    public void setMinGames(Integer minGames) {
        this.minGames = minGames;
    }

    public Integer getMaxGames() {
        return maxGames;
    }

    public void setMaxGames(Integer maxGames) {
        this.maxGames = maxGames;
    }

    public List<FutsalPositionEnum> getPreferredPositions() {
        return preferredPositions;
    }

    public void setPreferredPositions(List<FutsalPositionEnum> preferredPositions) {
        this.preferredPositions = preferredPositions;
    }

    public enum RedCardOrder {
        ASC, DSC
    }

}
