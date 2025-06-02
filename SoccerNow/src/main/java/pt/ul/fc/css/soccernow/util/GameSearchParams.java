package pt.ul.fc.css.soccernow.util;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameSearchParams {
    List<GameStatusEnum> statuses = new ArrayList<>();

    private Integer numGoals;
    private Integer minGoals;
    private Integer maxGoals;

    private String country;
    private String city;
    private String street;
    private String postalCode;
    @NotNull
    private TimeOfDay timeOfDay = TimeOfDay.MORNING;

    public List<GameStatusEnum> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<GameStatusEnum> statuses) {
        this.statuses = statuses;
    }

    public Integer getNumGoals() {
        return numGoals;
    }

    public void setNumGoals(Integer numGoals) {
        this.numGoals = numGoals;
    }

    public Integer getMinGoals() {
        return minGoals;
    }

    public void setMinGoals(Integer minGoals) {
        this.minGoals = minGoals;
    }

    public Integer getMaxGoals() {
        return maxGoals;
    }

    public void setMaxGoals(Integer maxGoals) {
        this.maxGoals = maxGoals;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

}
