package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Help extends A_Command implements  Command, Serializable {
    public void Command_Help(){

    }
    @Override
    public ArrayList<String> execute() { return Receiver.help();

    }

}
