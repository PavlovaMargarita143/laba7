package com.company;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_RemoveAt extends A_Command implements Command, Serializable {
    ArrayList<City> set;
    int index;
    String login;
    Command_RemoveAt(int index){
        this.index=index;

    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public ArrayList<String> execute() throws SQLException {
        return Receiver.remove(A_Command.getSet(),index, login);

    }
    @Override
    public boolean getStatus() {
        return true;
    }
}

