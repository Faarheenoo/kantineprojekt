package server.models;

import java.util.ArrayList;

/*This class is used to define an order in the system,
 which contain get and set methods for them and constructors*/


public class Order {


    private int id;
    private int userId;
    private String date;

    private ArrayList<Item> items = new ArrayList();


    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Order(int id, String date, int user_id) {
        this.id = id;
        this.date = date;
        this.userId = user_id;
    }

    public Order() {

    }




}



