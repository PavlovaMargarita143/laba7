package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Show extends A_Command implements Command, Serializable {


    public Command_Show( ){

    }
    @Override
    public ArrayList<String> execute() {
        return Receiver.show(A_Command.getSet());


    }

}
