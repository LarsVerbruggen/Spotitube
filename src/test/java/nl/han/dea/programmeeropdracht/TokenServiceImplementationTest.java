package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.services.TokenServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenServiceImplementationTest {

    TokenServiceImplementation sut;
    LoginDAO loginDAO;
    String token;

    @BeforeEach
    void setup(){
        sut = new TokenServiceImplementation();
        loginDAO = mock(LoginDAO.class);
        sut.setLoginDAO(loginDAO);
        token = "21321-515az";
    }

    @Test
    void doesTokenReturnUsername(){
        // Setup
        when(loginDAO.getUserByToken(token)).thenReturn("Lars");

        // Test
        String actual = sut.getUserByToken(token);

        // Verify
        assertEquals("Lars", actual);
    }

}
