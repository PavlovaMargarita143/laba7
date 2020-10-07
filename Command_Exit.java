package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Exit extends A_Command implements  Command, Serializable {
    public Command_Exit(){

    }
    @Override
    public ArrayList<String> execute() {
       return Receiver.exit();

    }
}
