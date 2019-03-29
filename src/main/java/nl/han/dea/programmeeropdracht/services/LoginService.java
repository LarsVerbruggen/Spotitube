package nl.han.dea.programmeeropdracht.services;

import javax.ws.rs.core.Response;

public interface LoginService {
    Response login(String user, String password);
}
