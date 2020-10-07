package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_SumOfSeaLevel extends A_Command implements Command, Serializable {
    public Command_SumOfSeaLevel(){}
    @Override
    public ArrayList<String> execute() {
       return Receiver.sumOfSeaL(A_Command.getSet());

    }

}
