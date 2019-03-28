package nl.han.dea.programmeeropdracht.services;

import java.util.UUID;

public class TokenGenerator {

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

}
