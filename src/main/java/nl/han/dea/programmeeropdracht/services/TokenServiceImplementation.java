package nl.han.dea.programmeeropdracht.services;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;

import javax.inject.Inject;

public class TokenServiceImplementation implements TokenService {

    private LoginDAO loginDAO;

    @Override
    public String getUserByToken(String token) {
        return loginDAO.getUserByToken(token);
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO){
        this.loginDAO = loginDAO;
    }
}
