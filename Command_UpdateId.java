package com.company;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_UpdateId extends A_Command implements Command, Serializable {
    int id;
    City newCity;
    String login;
    public Command_UpdateId(int id, City newCity){
        this.id= id;
        this.newCity = newCity;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
    @Override
    public ArrayList<String> execute() throws SQLException {
       return Receiver.update(A_Command.getSet(),this.id, newCity, login);

    }
    @Override
    public boolean getStatus() {
        return true;
    }
}


