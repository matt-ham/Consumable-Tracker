package ca.cmpt213.a4.webappserver.control;

import ca.cmpt213.a4.webappserver.model.Consumable;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


/**
 * Class that handles action to do
 * on food item depending on system input
 *
 * @author Matthew Hamilton
 */
public class ConsumableManager {

    private static ArrayList<Consumable> itemList = new ArrayList<>();
    private static final String JSON_PATH = "./itempath.json";

    public static ArrayList<Consumable> addItem(Consumable consumable) {
        itemList.add(consumable);
        Collections.sort(itemList);
        return itemList;
    }

    public static ArrayList<Consumable> getList() {
        Collections.sort(itemList);
        return itemList;
    }

    public static ArrayList<Consumable> removeItemByIndex(int index) {
        itemList.remove(index);
        Collections.sort(itemList);
        return itemList;
    }

    public static Boolean isExpired(LocalDateTime date, LocalDateTime today) {
        if (today.getYear() == date.getYear() && today.getDayOfYear() == date.getDayOfYear()) {
            return false;
        } else return !today.isBefore(date);
    }

    public static List<Consumable> expiredItems() {
        List<Consumable> expiredList = new ArrayList<>();

        Collections.sort(itemList);

        for (Consumable item : itemList) {

            if (isExpired(item.expiryDate(), LocalDateTime.now())) {
                expiredList.add(item);
            }
        }
        return expiredList;
    }


    public static List<Consumable> nonExpired() {
        List<Consumable> notExpiredList = new ArrayList<>();
        Collections.sort(itemList);

        for (Consumable item : itemList) {

            if (!isExpired(item.expiryDate(), LocalDateTime.now())) {
                notExpiredList.add(item);
            }
        }
        return notExpiredList;
    }

    public static List<Consumable> expiryIn7Items() {
        List<Consumable> expired7List = new ArrayList<>();

        Collections.sort(itemList);

        for (Consumable item : itemList) {

            long daysTillExpired = item.expiringDays();

            if (!isExpired(item.expiryDate(), LocalDateTime.now())
                    && daysTillExpired <= 7) {
                expired7List.add(item);
            }
        }
        return expired7List;
    }

    public static void saveFoodFile() {
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                new TypeAdapter<LocalDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter,
                                      LocalDateTime localDateTime) throws IOException {
                        jsonWriter.value(localDateTime.toString());
                    }

                    @Override
                    public LocalDateTime read(JsonReader jsonReader) throws IOException {
                        return LocalDateTime.parse(jsonReader.nextString());
                    }
                }).create();
        try {
            Writer writer = new FileWriter(JSON_PATH);
            myGson.toJson(itemList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
