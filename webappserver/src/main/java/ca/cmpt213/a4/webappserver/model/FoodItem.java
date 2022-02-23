package ca.cmpt213.a4.webappserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Class for FoodItem Object
 * @author matthew
 */
public class FoodItem implements Consumable{

    private String foodName;
    private String notes;
    private double price;
    private double weight;
    private String expiryDate;
    private int choice;


    public FoodItem(@JsonProperty("foodName") String foodName,@JsonProperty("notes") String notes,@JsonProperty("price") double price,
                    @JsonProperty("weight") double weight,@JsonProperty("expiryDate") String expiryDate,@JsonProperty("choice") int choice) {

        this.foodName = foodName;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.choice = choice;

    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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
