package com.company;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_RemoveById extends A_Command implements Command, Serializable {
    int id;
    String login;
    public Command_RemoveById (int id){
        this.id =id;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
    @Override
    public ArrayList<String> execute() throws SQLException {
 return  Receiver.removeById(A_Command.getSet(),id,login );

    }
    @Override
    public boolean getStatus() {
        return true;
    }
}
