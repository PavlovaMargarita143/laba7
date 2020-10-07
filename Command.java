package com.company;

//import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Command {
    ArrayList<String> execute() throws SQLException;
    public boolean getStatus();
}
