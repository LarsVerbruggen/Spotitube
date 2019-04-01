package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController {

    LoginService service;

    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request) {
        return service.login(request.getUser(), request.getPassword());
    }

    @Inject
    public void setLoginService(LoginService service){
        this.service = service;
    }

}
