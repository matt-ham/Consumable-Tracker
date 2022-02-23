package ca.cmpt213.a4.webappserver.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for DrinkItem Object
 * @author matthew
 */
public class DrinkItem implements Consumable{

    private String drinkName;
    private String notes;
    private double price;
    private double volume;
    private String expiryDate;
    private int choice;

    public DrinkItem(@JsonProperty("drinkName") String drinkName, @JsonProperty("notes") String notes, @JsonProperty("price") double price,
                    @JsonProperty("volume") double volume, @JsonProperty("expiryDate") String expiryDate, @JsonProperty("choice") int choice) {

        this.drinkName = drinkName;
        this.notes = notes;
        this.price = price;
        this.volume = volume;
        this.expiryDate = expiryDate;
        this.choice = choice;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    @Override
    public LocalDateTime expiryDate() {
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(expiryDate, timePattern);
        return date;
    }

    @Override
    public long expiringDays() {
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(expiryDate, timePattern);
        return Math.abs(Duration.between(LocalDateTime.now(), date).toDays());
    }

    @Override
    public int compareTo(Consumable o) {
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(expiryDate, timePattern);
        return date.compareTo(o.expiryDate());
    }
}
