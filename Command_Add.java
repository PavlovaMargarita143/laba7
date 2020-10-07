package com.company;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_Add extends A_Command implements Command, Serializable {
String login;
    City newCity;
    public Command_Add( City newCity){

        this.newCity=newCity;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public ArrayList<String> execute() throws SQLException {
       return Receiver.add(newCity,A_Command.getSet(), login);

    }
    @Override
    public boolean getStatus() {
        return true;
    }



}
