package nl.han.dea.programmeeropdracht.Controller;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;
import nl.han.dea.programmeeropdracht.model.UserModel;
import nl.han.dea.programmeeropdracht.services.LoginServiceImplementation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController {

    @Inject
    LoginServiceImplementation service;

    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request) {
        return service.login(request.getUser(), request.getPassword());
    }

}
