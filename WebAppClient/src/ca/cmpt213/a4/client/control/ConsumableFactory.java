package ca.cmpt213.a4.client.control;


import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.DrinkInfo;
import ca.cmpt213.a4.client.model.FoodInfo;

import java.time.LocalDateTime;

/**
 * Class for ConsumableFactory.
 * Creates Drink Info / Food Info object
 * depending on the users input
 *
 * @author matthew
 */
public class ConsumableFactory {


    public Consumable getInstance(int choice, String name, String notes, double size, double price, LocalDateTime date) {
        if (choice == 2) {
            return new DrinkInfo(choice, name, notes, size, price, date);
        } else if (choice == 1) {
            return new FoodInfo(choice, name, notes, size, price, date);
        } else {
            return null;
        }
    }
}
