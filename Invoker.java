package com.company;

//import java.io.IOException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Invoker implements Serializable {
    Command command;

    public void setCommand(Command command) {
        this.command = command;
    }
    public ArrayList<String> executeCommand() throws SQLException {
       return command.execute();

    }
}
