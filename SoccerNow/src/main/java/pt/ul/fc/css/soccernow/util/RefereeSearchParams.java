package pt.ul.fc.css.soccernow.util;

public class RefereeSearchParams {
    private String name;

    private Integer numGames;
    private Integer minGames;
    private Integer maxGames;

    private Integer numCardsGiven;
    private Integer minCardsGiven;
    private Integer maxCardsGiven;

    private Integer size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getNumCardsGiven() {
        return numCardsGiven;
    }

    public void setNumCardsGiven(Integer numCardsGiven) {
        this.numCardsGiven = numCardsGiven;
    }

    public Integer getMinCardsGiven() {
        return minCardsGiven;
    }

    public void setMinCardsGiven(Integer minCardsGiven) {
        this.minCardsGiven = minCardsGiven;
    }

    public Integer getMaxCardsGiven() {
        return maxCardsGiven;
    }

    public void setMaxCardsGiven(Integer maxCardsGiven) {
        this.maxCardsGiven = maxCardsGiven;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
