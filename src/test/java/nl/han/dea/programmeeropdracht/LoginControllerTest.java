package nl.han.dea.programmeeropdracht;

import nl.han.dea.programmeeropdracht.Controller.LoginController;
import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;
import nl.han.dea.programmeeropdracht.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

public class LoginControllerTest {
    public static final String USERNAME = "Lars";
    public static final String PASSWORD = "909090";

    private LoginController loginController;
    private LoginDAO loginDaoMock;

    @BeforeEach
    void setup(){
        loginDaoMock = Mockito.mock(LoginDAO.class);
        loginController = new LoginController();
        loginController.setLoginDAO(loginDaoMock);
    }

    @Test
    void doesEndpointDelegateToDAO(){
        // Setup
        var dto = new LoginRequest();
        dto.setUser(USERNAME);
        dto.setPassword(PASSWORD);

        var user = new UserModel();
        user.setName(USERNAME);
        user.setToken("1312315-wad");
        Mockito.when(loginDaoMock.getLoginCredentials(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        Response response = loginController.login(dto);


        // Verifiy
        Mockito.verify(loginDaoMock).getLoginCredentials(USERNAME, PASSWORD);
        Assertions.assertEquals(200, response.getStatus());
    }


}
