package ca.cmpt213.a4.webappserver.controllers;

import ca.cmpt213.a4.webappserver.control.ConsumableManager;
import ca.cmpt213.a4.webappserver.model.Consumable;
import ca.cmpt213.a4.webappserver.model.DrinkItem;
import ca.cmpt213.a4.webappserver.model.FoodItem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller class, takes server requests
 *
 * @author matthew
 */
@RestController
public class ConsumableController {

    @GetMapping("/ping")
    public String ping() {
        return "System is up!";
    }

    @PostMapping("/addFoodItem")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> addFoodItem(@RequestBody FoodItem food) {
        return ConsumableManager.addItem(food);
    }

    @PostMapping("removeItem/{index}")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> removeItem(@PathVariable("index") int index) {
        return ConsumableManager.removeItemByIndex(index);
    }

    @PostMapping("/addDrinkItem")
    @ResponseStatus(HttpStatus.CREATED)
    public ArrayList<Consumable> addDrinkItem(@RequestBody DrinkItem drink) {
        return ConsumableManager.addItem(drink);
    }

    @GetMapping("/listAll")
    public List<Consumable> listItems() {
        return ConsumableManager.getList();
    }

    @GetMapping("/listExpired")
    public List<Consumable> listExpired() {
        return ConsumableManager.expiredItems();
    }

    @GetMapping("/listNonExpired")
    public List<Consumable> listNonExpired() {
        return ConsumableManager.nonExpired();
    }

    @GetMapping("/listExpiringIn7Days")
    public List<Consumable> listExpiringIn7Days() {
        return ConsumableManager.expiryIn7Items();
    }

    @GetMapping("/exit")
    public void exit() {
        ConsumableManager.saveFoodFile();
    }


}
