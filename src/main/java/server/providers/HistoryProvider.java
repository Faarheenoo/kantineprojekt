package server.providers;

import server.models.Item;
import server.models.Order;
import server.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static server.ServerImplDB.ImplDB.getConnection;

/* This class contains the SQL statements */
/*first we choose order with userid, and then user_id with item.*/

public class HistoryProvider {

    public ResultSet getOrders( /*string tablnename */ int user_id) throws Exception {
        PreparedStatement sql = getConnection().prepareStatement("SELECT * FROM KantineWebpage.order WHERE user_id  = ?");

        sql.setInt(1, user_id);

        return sql.executeQuery();
    }

    public ResultSet getProductsByOrder(int order_id) throws Exception {

        PreparedStatement sql = getConnection().prepareStatement("SELECT i.id as item_id, p.* FROM KantineWebpage.items i\n" +
                "INNER JOIN lol.product p\n" +
                "ON i.products_id = p.id\n" +
                "WHERE order_id = ?;");
        sql.setInt(1, order_id);
        //sql.setInt(1, order_id );
        return sql.executeQuery();
    }

    public ArrayList<Order> getOrders() {

        ArrayList<Order> orders = new ArrayList<>();

        try {
            ResultSet results = getOrders(1);
            System.out.println("results!: " + results);

            while (results.next()) {
                Order order = new Order(
                        results.getInt("id"),
                        results.getString("date"),
                        results.getInt("user_id")

                );
                orders.add(order);
            }
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Order> ordersResult = new ArrayList<>();

        for(int i = 0; i <= orders.size(); i++){
            try {

                Order order = orders.get(i);
                ResultSet productResults = getProductsByOrder(order.getId());

                while (productResults.next()) {

                    Product product = new Product();
                    product.setProductName(productResults.getString("name"));
                    product.setProductPrice(productResults.getString("price"));
                    product.setId(productResults.getInt("id"));

                    Item item = new Item();
                    item.setProduct(product);
                    item.setId(productResults.getInt("item_id"));
                    item.setProducts_id(productResults.getInt("id"));

                    order.addItem(item);
                    ordersResult.add(order);
                }
                productResults.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ordersResult;
    }
}
