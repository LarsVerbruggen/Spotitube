package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.LoginController;
import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {
    public static final String USERNAME = "Lars";
    public static final String PASSWORD = "909090";

    private LoginController loginController;
    private LoginDAO loginDaoMock;

    @BeforeEach
    void setup(){
        loginDaoMock = mock(LoginDAO.class);
        loginController = new LoginController();
        loginController.setLoginDAO(loginDaoMock);
    }

    @Test
    void doesEndpointDelegateToDAO(){
        // setup
        var dto = new LoginRequest();
        dto.setUser("");
        dto.setPassword("");
        when(loginDaoMock.getLoginCredentials("", "")).thenReturn(new UserModel());


        // Test
        loginController.login(dto);


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
        Response response = loginController.login(dto);


        // Verifiy
        assertEquals(200, response.getStatus());
    }

    @Test
    void cantLoginWithWrongCredentials(){
        // setup
        var dto = new LoginRequest();
        dto.setUser("");
        dto.setPassword("");
        when(loginDaoMock.getLoginCredentials("", "")).thenReturn(new UserModel());
        //loginController.setLoginDAO(loginDaoMock);

        // Test
        Response login = loginController.login(dto);


        // Verify
        assertEquals(403, login.getStatus());
    }


}
