package server.endpoints;

import com.google.gson.Gson;
import server.ServerImplDB.ImplDB;
import server.models.Product;
import server.models.User;
import server.providers.FoodProvider;
import server.providers.UserProvider;
import server.utility.Kryptering;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/* This class is used to GET data from the Database, only food products */
/* The token must be sent "authorization"*/

@Path("/food")
public class FoodEndpoint {
    UserProvider userProvider = new UserProvider();

    @GET
    public Response getAllFoods(@HeaderParam("Authorization") String token) throws Exception {
        User u  = userProvider.getUserFromToken(token);

        if(u!=null && token !=null) {
            ImplDB serverImplDB = new ImplDB();
            ArrayList<Product> allProducts = new FoodProvider().getFoods();

            String json = new Gson().toJson(allProducts);

            String krypteret = Kryptering.encryptdecrypt(json);
            krypteret = new Gson().toJson(krypteret);

            return Response.status(200).type("application/json").entity(krypteret).build();
        } else {
            return Response.status(401).build();
        }

    }
}

