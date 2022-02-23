package ca.cmpt213.a4.client.model;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Class that handles the information of the item.
 * Includes methods to check if expired & an overloaded
 * toString method which returns the info of item.
 *
 * @author Matthew Hamilton
 */
public class DrinkInfo implements Consumable {

    private String drinkName;
    private String notes;
    private double price;
    private LocalDateTime expiryDate;
    private double volume;
    private int choice;

    public DrinkInfo(int choice, String drinkName, String notes, double price, double volume, LocalDateTime expiryDate) {
        this.choice = choice;
        this.drinkName = drinkName;
        this.notes = notes;
        this.price = price;
        this.expiryDate = expiryDate;
        this.volume = volume;
    }


    @Override
    public LocalDateTime expiryDate() {
        return expiryDate;
    }

    /**
     * Method which calculates the days until expiry of item
     *
     * @return days until expiration date.
     */
    @Override
    public long expiringDays() {
        return Math.abs(Duration.between(LocalDateTime.now(), expiryDate).toDays());
    }


    /**
     * method which returns if the date is
     * expired or not.
     *
     * @return isExpired (T/f)
     */
    @Override
    public boolean isExpired() {
        LocalDateTime date = expiryDate;
        LocalDateTime today = LocalDateTime.now();
        if (today.getYear() == date.getYear() && today.getDayOfYear() == date.getDayOfYear()) {
            return false;
        } else return !today.isBefore(date);
    }


    /**
     * Compares item objects expiry dates
     * Ranks oldest first
     */
    @Override
    public int compareTo(Consumable o) {
        return expiryDate.compareTo(o.expiryDate());
    }


    //https://stackoverflow.com/questions/1090098/newline-in-jlabel

    /**
     * Method that prints food information.
     * Chooses between to return options: expired
     * and not expired
     *
     * @return String of item info
     */
    @Override
    public String toString() {

        String roundedDoubleStr = String.format("%.2f", price);
        String roundedVolumeStr = String.format("%.2f", volume);

        DateTimeFormatter patternDates = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String newFormattedExpiryDates = expiryDate.format(patternDates);
        if (isExpired()) {

            long daysExpired = Math.abs(Duration.between(LocalDateTime.now(), expiryDate).toDays());
            String daysExpString = "has been expired for " + daysExpired + " day(s)";
            return "<html>Name: " + drinkName +
                    "<BR>Notes: " + notes +
                    "<BR>Volume: " + roundedVolumeStr +
                    "<BR>Price: " + roundedDoubleStr +
                    "<BR>Expiry date: " + newFormattedExpiryDates
                    + "<BR>This food item expires " + daysExpString + "<BR><html>";
        } else {
            long daysTillExpired = Math.abs((expiryDate.getDayOfYear() - LocalDateTime.now().getDayOfYear()));
            String daysTillExpString = "in " + daysTillExpired + " day(s)";
            if (daysTillExpired == 0) {
                daysTillExpString = "today";
            }

            return "<html>Name: " + drinkName +
                    "<BR>Notes: " + notes +
                    "<BR>Weight: " + roundedVolumeStr +
                    "<BR>Price: " + roundedDoubleStr +
                    "<BR>Expiry date: " + newFormattedExpiryDates
                    + "<BR>This food item expires " + daysTillExpString + "<BR><html>";
        }

    }

}
