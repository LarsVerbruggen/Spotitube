package nl.han.dea.programmeeropdracht.services;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;
import nl.han.dea.programmeeropdracht.model.UserModel;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class LoginServiceImplementation implements LoginService{

    private LoginDAO loginDAO;

    @Override
    public Response login(String user, String password) {
        UserModel result = loginDAO.getLoginCredentials(user, password);
        LoginResponse response = new LoginResponse();

        if(result.getName() == null && result.getToken() == null){
            return Response.status(401).build();
        }else {
            String token = UUID.randomUUID().toString();
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
