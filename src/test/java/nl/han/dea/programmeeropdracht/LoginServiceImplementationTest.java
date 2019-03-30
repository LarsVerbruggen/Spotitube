package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.model.UserModel;
import nl.han.dea.programmeeropdracht.services.LoginServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceImplementationTest {
    private static final String USERNAME = "Lars";
    private static final String PASSWORD = "909090";

    private LoginServiceImplementation loginService;
    private LoginDAO loginDaoMock;

    @BeforeEach
    void setup(){
        loginDaoMock = mock(LoginDAO.class);
        loginService = new LoginServiceImplementation();
        loginService.setLoginDAO(loginDaoMock);
    }

    @Test
    void doesEndpointDelegateToDAO(){
        // setup
        var dto = new LoginRequest();
        dto.setUser("");
        dto.setPassword("");
        when(loginDaoMock.getLoginCredentials("", "")).thenReturn(new UserModel());


        // Test
        loginService.login(dto.getUser(), dto.getPassword());


        // Verify
        verify(loginDaoMock).getLoginCredentials("", "");
    }

    @Test
    void canLoginWithCorrectCredentials(){
        // Setup
        var dto = new LoginRequest();
        dto.setUser(USERNAME);
        dto.setPassword(PASSWORD);

        var user = new UserModel();
        user.setName(USERNAME);
        user.setToken("1312315-wad");
        when(loginDaoMock.getLoginCredentials(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        Response response = loginService.login(dto.getUser(), dto.getPassword());


        // Verifiy
        assertEquals(200, response.getStatus());
    }

    @Test
    void cantLoginWithWrongCredentials(){
        // setup
        var dto = new LoginRequest();
        dto.setUser("Lars");
        dto.setPassword("1234");

        when(loginDaoMock.getLoginCredentials("Lars", "1234")).thenReturn(new UserModel());

        // Test
        Response login = loginService.login(dto.getUser(), dto.getPassword());


        // Verify
        assertEquals(401, login.getStatus());
    }


}
