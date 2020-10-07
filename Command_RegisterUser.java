package com.company;

import server.Database_Reciver;
import server.Server;
import server.User;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Command_RegisterUser extends A_Command implements Command, Serializable {



    User user;

    public Command_RegisterUser(User user) {
        this.user = user;
    }

    public Command_RegisterUser() {

    }


    @Override
    public ArrayList<String> execute() throws SQLException {
        try {
            return Database_Reciver.registerUser(this.user, Server.getDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<String>(Collections.singleton(e.getMessage()));
        }
    }



}
