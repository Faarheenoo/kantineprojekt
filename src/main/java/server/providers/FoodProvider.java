package server.providers;

import server.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static server.ServerImplDB.ImplDB.getConnection;

/* This class contains the SQL statements, and arraylist */

public class FoodProvider {
    public ResultSet getFoods(String tablename) throws Exception {
        PreparedStatement sql = getConnection().prepareStatement("SELECT * FROM KantineWebpage.product WHERE type = 1");


        System.out.println(sql.executeQuery());
        return sql.executeQuery();
    }

    public ArrayList<Product> getFoods() {
        ArrayList<Product> products = new ArrayList<>();

        try {
            ResultSet results = getFoods("Product");
            System.out.println("results!: " + results);

            while (results.next()) {
                Product product = new Product(
                        results.getInt("type"),
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("price")
                );

                products.add(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;


    }
}
