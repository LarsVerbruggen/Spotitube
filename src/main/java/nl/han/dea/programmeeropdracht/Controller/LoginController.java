package nl.han.dea.programmeeropdracht.Controller;


import nl.han.dea.programmeeropdracht.Database.DatabaseConnection;
import nl.han.dea.programmeeropdracht.Database.LoginDAO;
import nl.han.dea.programmeeropdracht.dto.LoginRequest;
import nl.han.dea.programmeeropdracht.dto.LoginResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/")
public class LoginController {
    DatabaseConnection dbcon = new DatabaseConnection();
    private LoginDAO loginDAO = new LoginDAO();


    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request){
        ResultSet result = loginDAO.getLoginCredentials(request.getUser());
        LoginResponse response = new LoginResponse();

        try{
            while (result.next()){
                if(request.getPassword().equals(result.getString("PASSWORD")))
                    response.setUser(result.getString("USER_NAME"));
                    response.setToken(result.getString("TOKEN"));
            }
        } catch (SQLException e){
            System.out.println("Error getting results: " + e);
        }





        return Response.ok(response).build();
    }

}
