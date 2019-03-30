package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.LoginController;
import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.services.LoginServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController controller;
    private LoginServiceImplementation service;
    private LoginRequest request;

    @BeforeEach
    void setup(){
        controller = new LoginController();
        service = mock(LoginServiceImplementation.class);
        controller.setLoginService(service);
        request = new LoginRequest();
        request.setUser("Lars");
        request.setPassword("password");
    }

    @Test
    void doesLoginControllerMakeUseOfService(){
        // Setup
        when(service.login(any(), any())).thenReturn(Response.ok().build());

        // Test
        controller.login(request);

        // Verify
        verify(service).login(request.getUser(), request.getPassword());
    }

}
