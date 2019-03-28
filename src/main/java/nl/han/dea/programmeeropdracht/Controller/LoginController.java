package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;
import nl.han.dea.programmeeropdracht.model.UserModel;
import nl.han.dea.programmeeropdracht.services.TokenGenerator;

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

        if(result.getName() == null && result.getToken() == null){
            return Response.status(401).build();
        }else if( result.getToken() == null){
            return Response.status(403).build();
        }else{
            TokenGenerator generator = new TokenGenerator();
            String token = generator.generateToken();
            loginDAO.updateToken(result.getName(), token);
            response.setToken(token);
        }
        response.setUser(result.getName());


        return Response.ok(response).build();
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }

}
