package nl.han.dea.programmeeropdracht.Controller;


import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController {


    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request){
        String token = "314156yhrsfad-a";
        String user = "Lars";

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(user);

        return Response.ok(response).build();
    }

}
