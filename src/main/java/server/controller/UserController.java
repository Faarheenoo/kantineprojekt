package server.controller;

import server.models.User;
import server.utility.Digester;
import server.providers.UserProvider;
import java.util.ArrayList;

/*This UserController class is used to combine our endpoints with the providers
 so that we are able to create a user with a hashed password.*/


public class UserController {


    private Digester digester = new Digester();
    private UserProvider userProvider = new UserProvider();

    public boolean addUser(User user) {
        String hashedPassword = digester.hashWithSalt(user.getPassword());
        user.setPassword(hashedPassword);
        boolean result = userProvider.createUser(user);
        return result;
    }
}
