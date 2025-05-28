package pt.ul.fc.css.soccernow.util;

public class RefereeSearchParams {
    private String refereeName;

    private Integer numGames;
    private Integer minNumGames;
    private Integer maxNumGames;

    private Integer numCardsGiven;
    private Integer minNumCardsGiven;
    private Integer maxNumCardsGiven;

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
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

    public Integer getNumCardsGiven() {
        return numCardsGiven;
    }

    public void setNumCardsGiven(Integer numCardsGiven) {
        this.numCardsGiven = numCardsGiven;
    }

    public Integer getMinNumCardsGiven() {
        return minNumCardsGiven;
    }

    public void setMinNumCardsGiven(Integer minNumCardsGiven) {
        this.minNumCardsGiven = minNumCardsGiven;
    }

    public Integer getMaxNumCardsGiven() {
        return maxNumCardsGiven;
    }

    public void setMaxNumCardsGiven(Integer maxNumCardsGiven) {
        this.maxNumCardsGiven = maxNumCardsGiven;
    }
}
