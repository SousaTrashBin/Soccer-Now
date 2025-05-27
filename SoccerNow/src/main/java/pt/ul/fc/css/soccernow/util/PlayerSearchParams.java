package pt.ul.fc.css.soccernow.util;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class PlayerSearchParams {
    @Min(1)
    private Integer size;

    @Pattern(regexp = "asc|dsc", message = "Order must be 'asc' or 'dsc'")
    private String redCardComparatorOrder;

    private String playerName;
    private FutsalPositionEnum preferredPosition;

    private Integer numReceivedCards;
    private Integer minReceivedCards;
    private Integer maxReceivedCards;

    private Integer scoredGoals;
    private Integer minScoredGoals;
    private Integer maxScoredGoals;

    private Integer numGames;
    private Integer minNumGames;
    private Integer maxNumGames;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getRedCardComparatorOrder() {
        return redCardComparatorOrder;
    }

    public void setRedCardComparatorOrder(String redCardComparatorOrder) {
        this.redCardComparatorOrder = redCardComparatorOrder;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public FutsalPositionEnum getPreferredPosition() {
        return preferredPosition;
    }

    public void setPreferredPosition(FutsalPositionEnum preferredPosition) {
        this.preferredPosition = preferredPosition;
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

    public Integer getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(Integer scoredGoals) {
        this.scoredGoals = scoredGoals;
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

    public Integer getMinNumGames() {
        return minNumGames;
    }

    public void setMinNumGames(Integer minNumGames) {
        this.minNumGames = minNumGames;
    }

    public Integer getMaxNumGames() {
        return maxNumGames;
    }

    public void setMaxNumGames(Integer maxNumGames) {
        this.maxNumGames = maxNumGames;
    }

}
