package server.endpoints;

import com.google.gson.Gson;
import server.ServerImplDB.ImplDB;
import server.models.Order;
import server.models.User;
import server.providers.HistoryProvider;
import server.providers.UserProvider;
import server.utility.Kryptering;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/* This class GETs all the order the a specifik person from Database */
/* The token must be sent "authorization"*/

@Path("/history")
public class HistoryEndpoint {
    UserProvider userProvider = new UserProvider();

    @GET
    public Response getAllOrder(@HeaderParam("Authorization") String token) throws Exception {
        User u = userProvider.getUserFromToken(token);
        if(u!=null && token!=null) {
            ImplDB serverImplDB = new ImplDB();
            ArrayList<Order> allOrders = new HistoryProvider().getOrders();

            String json = new Gson().toJson(allOrders);

            String krypteret = Kryptering.encryptdecrypt(json);
            krypteret = new Gson().toJson(krypteret);

            return Response.status(200).type("application/json").entity(krypteret).build();
        } else {
            return Response.status(400).entity(new Gson().toJson("Error")).build();
        }


    }
}

