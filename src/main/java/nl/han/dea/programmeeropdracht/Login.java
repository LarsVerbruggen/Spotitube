package nl.han.dea.programmeeropdracht;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class Login {


    @GET
    @Produces("text/html")
    public String helloWorld(){
        return "Hello World";
    }

}
