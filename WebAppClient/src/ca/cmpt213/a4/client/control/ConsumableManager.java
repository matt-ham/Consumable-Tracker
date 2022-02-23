package ca.cmpt213.a4.client.control;

import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.model.DrinkInfo;
import ca.cmpt213.a4.client.model.FoodInfo;
import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ConsumableManager {



    private static List<Consumable> itemList = new ArrayList<>();

    public ConsumableManager() {

    }


    public void addItem(Consumable item) {
        itemList.add(item);
    }

    public List<Consumable> expiredItems() {


        List<Consumable> expiredList = new ArrayList<>();

        Collections.sort(itemList);

        for (Consumable item : itemList) {

            if (item.isExpired()) {
                expiredList.add(item);
            }
        }
        return expiredList;
    }

    public List<Consumable> expiryIn7Items() {
        List<Consumable> expired7List = new ArrayList<>();

        Collections.sort(itemList);

        for (Consumable item : itemList) {

            long daysTillExpired = item.expiringDays();

            if (!item.isExpired()
                    && daysTillExpired <= 7) {
                expired7List.add(item);
            }
        }
        return expired7List;
    }

    public List<Consumable> nonExpired() {
        List<Consumable> notExpiredList = new ArrayList<>();
        Collections.sort(itemList);

        for (Consumable item : itemList) {

            if (!item.isExpired()) {
                notExpiredList.add(item);
            }
        }
        return notExpiredList;
    }

    public List<Consumable> allItems() {
        return itemList;
    }

    public void removeItem(Consumable consumable, int idx) {
        try {
            removeItemFromServer(idx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemList.remove(consumable);
    }

    private void removeItemFromServer(int index) throws IOException {

        URL url = new URL("http://localhost:8080/removeItem/" + index);


        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.connect();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(url)))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(index)))
                .build();
        try {
            HttpResponse<String> HTTPResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(HTTPResponse.body());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void addItemToServer(int choice, String consumable) throws IOException {

        URL url = new URL("http://localhost:8080/addFoodItem");
        if (choice == 2) {
            url = new URL("http://localhost:8080/addDrinkItem");
        }

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.connect();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(url)))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(consumable))
                .build();

        try {
            HttpResponse<String> HTTPResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(HTTPResponse.body());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveItem() throws IOException {

        URL url = new URL("http://localhost:8080/exit");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int response = connection.getResponseCode();

        if (response != 200) {
            throw new RuntimeException("RESPONSE: " + response);
        }
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            builder.append(scanner.nextLine());
        }
        scanner.close();
    }

    public static void loadArray() throws IOException {

        URL url = new URL("http://localhost:8080/listAll");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        Scanner scan = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();

        while (scan.hasNext()) {
            inline.append(scan.nextLine());
        }
        scan.close();

        JsonElement fileValue = JsonParser.parseString(inline.toString());
        JsonArray jsonList = fileValue.getAsJsonArray();

        for (int i = 0; i < jsonList.size(); i++) {
            JsonObject taskObj = jsonList.get(i).getAsJsonObject();
            String taskDate = taskObj.get("expiryDate").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(taskDate, formatter);
            int choice = taskObj.get("choice").getAsInt();

            if (choice == 1) {
                String foodName = taskObj.get("foodName").getAsString();
                String notes = taskObj.get("notes").getAsString();
                double weight = taskObj.get("weight").getAsDouble();
                double price = taskObj.get("price").getAsDouble();
                itemList.add(new FoodInfo(choice, foodName, notes, price, weight, localDateTime));
            } else if (choice == 2) {
                String drinkName = taskObj.get("drinkName").getAsString();
                String notes = taskObj.get("notes").getAsString();
                double volume = taskObj.get("volume").getAsDouble();
                double price = taskObj.get("price").getAsDouble();
                itemList.add(new DrinkInfo(choice, drinkName, notes, price, volume, localDateTime));
            }
        }
    }
}



