package ca.cmpt213.a4.client.model;


import java.time.LocalDateTime;


/**
 * interface which extends the Comparable interface
 * defines common methods for drink/foodInfo classes
 * compareTo method comes from the extension of Comparable
 *
 * @author Matthew Hamilton
 */
public interface Consumable extends Comparable<Consumable> {

    LocalDateTime expiryDate();

    long expiringDays();

    boolean isExpired();


}
