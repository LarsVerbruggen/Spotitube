package nl.han.dea.programmeeropdracht;


import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class Login {


    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request){

        if("piet".equals(request.getUser())){
            return Response.status(401).build();
        }

        String token = "314156yhrsfad-a";
        String user = "Lars";

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(user);

        return Response.ok(response).build();
    }

}
