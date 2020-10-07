package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Info extends A_Command implements Command, Serializable {
    public  Command_Info(){}

    @Override
    public ArrayList<String> execute() {
     return    Receiver.info(A_Command.getSet());


    }

}
