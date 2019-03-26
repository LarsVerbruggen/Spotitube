package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;
import nl.han.dea.programmeeropdracht.model.UserModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController {
    private LoginDAO loginDAO;


    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request) {
        UserModel result = loginDAO.getLoginCredentials(request.getUser(), request.getPassword());
        LoginResponse response = new LoginResponse();

        if(result.getToken() == null){
            return Response.status(403).build();
        }

        response.setToken(result.getToken());
        response.setUser(result.getName());


        return Response.ok(response).build();
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }

}
