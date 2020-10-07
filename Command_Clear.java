package com.company;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_Clear extends A_Command implements Command, Serializable {

String login;
    public Command_Clear( ){

    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public ArrayList<String> execute() throws SQLException {
        return Receiver.clear(A_Command.getSet(),this.login);
    }
    public String getLogin() {
        return login;
    }
    @Override
    public boolean getStatus() {
        return true;
    }
}
