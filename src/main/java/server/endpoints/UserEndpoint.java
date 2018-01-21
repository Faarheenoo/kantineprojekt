package server.endpoints;

import com.google.gson.Gson;
import server.controller.UserController;
import server.models.Order;
import server.models.User;
import server.providers.UserProvider;
import server.utility.Globals;
import server.utility.Kryptering;
import server.utility.Token;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
/* This class only contains POST method */

@Path("/users")
public class UserEndpoint {

    private UserController userController = new UserController();
    private UserProvider userProvider = new UserProvider();

/* create user method"*/

    @POST
    @Path("/create")
    public Response createUser(String jsonUser) {

        int status = 0;
        try {
            User userCreated = new Gson().fromJson(jsonUser, User.class);
            boolean result = userController.addUser(userCreated);
            status = 200;

            Globals.log.writeLog(getClass().getName(), this, "Creating user" + userCreated.getUsername() + " success", 0);
        } catch (Exception e)
        {
            if (e.getClass() == BadRequestException.class) {
                status = 400;

                Globals.log.writeLog(getClass().getName(), this, "Creating user failed", 2);
            } else if (e.getClass() == InternalServerErrorException.class){
                status = 500;
                Globals.log.writeLog(getClass().getName(), this, "Error 500", 1);
            }

        }
        return Response.status(status).type("application/json").entity("{\"userCreated\":\"true\"}").build();

    }

    /* Login method*/
    /* Must send username and password */

    @Path("/login")
    @POST
    public Response authorizeUser(String data) throws Exception {
        Token token = new Token();
        User user = new Gson().fromJson(data, User.class);
        User userFound = userProvider.authorizeUser(user.getUsername(), user.getPassword());
        String json = new Gson().toJson(data);


        if (userFound != null){

            String authToken = token.getToken(user.getUsername(), userFound.getId());

            Globals.log.writeLog(getClass().getName(), this, "User authorized", 2);

            return Response.status(200).entity(new Gson().toJson(Kryptering.encryptdecrypt(authToken))).build();
        } else {
            return Response.status(401).entity("Error").build();
        }

    }

    /* Log out method*/

    @Path("/logout")
    @POST
    public Response logout (String data) throws Exception {
        Token token = new Token();

        User userFound = userProvider.getUserFromToken(data);

        if (userProvider.deleteToken(userFound.getId())) {

            return Response.status(200).entity("Logged out").build();

        } else {
            return Response.status(401).entity("Error").build();
        }
    }

    /*  An order is places by order and user*/

    @Path("/order")
    @POST
    public Response createOrder(@HeaderParam("Authorization") String token, String data) throws Exception {
        User user = userProvider.getUserFromToken(token);
        Order order = new Gson().fromJson(data, Order.class);
        //String json = new Gson().toJson(data);


        if (user!=null && token!=null && userProvider.createOrder(order, user)) {
            return Response.status(200).entity(new Gson().toJson(order)).build();
        } else {
            return Response.status(401).entity("Error").build();
        }
    }
    /* Item is chosen to the order*/

    @Path("/order/{orderId}")
    @POST
    public Response addProductToOrder(@PathParam("orderId") int orderId, String data) throws Exception {
        String json = new Gson().toJson(orderId);

        if(userProvider.addProductToOrder(data, orderId)) {
            return Response.status(200).build();
        } else {
            return Response.status(401).entity("Error").build();
        }
    }


}

