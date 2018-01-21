package server.providers;

import server.models.Order;
import server.models.User;
import server.utility.Digester;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

import static server.ServerImplDB.ImplDB.getConnection;

public class UserProvider {


    private Connection connection;

/* Create user by specific parameter*/
    public boolean createUser(User user) throws IllegalArgumentException {
        try {
            connection = getConnection();
            PreparedStatement createUser = connection
                    .prepareStatement("INSERT INTO KantineWebpage.users (username, password, token) VALUES (?,?,?)");

            createUser.setString(1, user.getUsername());
            createUser.setString(2, user.getPassword());
            createUser.setString(3, user.getToken());

            createUser.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* Authorization, so only the matched username and password can log in*/
    /*and the pasword is hashed */

    public User authorizeUser(String username, String password) {
        try {
            Digester digester = new Digester();
            connection = getConnection();
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM KantineWebpage.users WHERE username = ? AND password = ?");
            sql.setString(1, username);
            sql.setString(2, digester.hashWithSalt(password));

            ResultSet resultSet = sql.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user.getUsername() + user.getPassword());
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*SQL statement that saves the token*/
    public User getToken(String token) throws Exception {
        try {
            User user = new User();
            connection = getConnection();
            PreparedStatement sql = connection.prepareStatement("INSERT INTO users where id = ?)");
            sql.setString(1, token);

            ResultSet resultSet = sql.executeQuery();

            while (resultSet.next()) {
                user.setToken(resultSet.getString("token"));
            }
            System.out.println(user.getUsername() + user.getPassword() + user.getToken());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*SQL statement that delete the token*/
    public boolean deleteToken(int id) throws Exception {
        try {
            User user = new User();
            connection = getConnection();
            PreparedStatement sql = connection.prepareStatement("UPDATE users SET token = ' ' WHERE id = ?");
            sql.setInt(1, id);
            sql.executeUpdate();
            user.setToken("-");
            System.out.println("Logged out");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /*User creates by orderid and user id*/
    public boolean createOrder(Order order, User user) throws IllegalArgumentException {

        try {
            connection = getConnection();

            PreparedStatement createOrder = connection
                    .prepareStatement("INSERT INTO KantineWebpage.order1 (date, user_id) VALUES (?,?)");

            createOrder.setString(1, order.getDate());
            createOrder.setInt(2, user.getId());


            createOrder.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*When user is created, we need userfromtoken, a security level*/
    public User getUserFromToken(String token) throws Exception {
        try {
            User user = new User();
            connection = getConnection();
            PreparedStatement sql = connection.prepareStatement("SELECT * FROM KantineWebpage.users WHERE token = ?");
            sql.setString(1, token);

            ResultSet resultSet = sql.executeQuery();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));

            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*Item chosen to order*/
    public boolean addProductToOrder(String products_id, int orderId) throws Exception {
        try {
            connection = getConnection();
            PreparedStatement PS = connection
                    .prepareStatement("INSERT INTO KantineWebpage.items (products_id, order_id) VALUE (?, ?)");
            PS.setInt(1, Integer.parseInt(products_id));
            PS.setInt(2, orderId);

            PS.executeUpdate();
            return true;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}