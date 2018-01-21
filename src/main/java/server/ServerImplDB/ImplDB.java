package server.ServerImplDB;

import java.sql.*;

public class ImplDB {

    private static Connection connection;

    public static Connection getConnection() throws Exception {
        System.out.println("Get connection!");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("try");
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // Setup the connection with the DB
            connection = DriverManager


            .getConnection("jdbc:mysql://localhost:3306/KantineWebpage", "LocalUser", "Farheen1234");


            System.out.println("Connection - - -- - - - - -- - - -- !: " + connection);
            return connection;
        } catch (Exception e) {
            System.out.println("Ramt exception!");
            System.out.println(e);
        }
        return null;
    }

}






