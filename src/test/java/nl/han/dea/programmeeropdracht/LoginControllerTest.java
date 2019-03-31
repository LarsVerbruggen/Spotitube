package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.LoginController;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.services.LoginServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        when(service.login(request.getUser(), request.getPassword())).thenReturn(Response.ok().build());

        // Test
        Response actual = controller.login(request);

//        // Verify
        assertEquals(200, actual.getStatus() );
    }

}
