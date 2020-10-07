package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Sort extends A_Command implements Command, Serializable {
    public  Command_Sort(){}
    @Override
    public ArrayList<String> execute() {
       return Receiver.sort(A_Command.getSet());

    }

}
