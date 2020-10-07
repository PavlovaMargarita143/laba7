package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_MaxCoordinates extends A_Command implements Command, Serializable {
    public Command_MaxCoordinates(){}
    @Override
    public ArrayList<String> execute() {
      return   Receiver.maxCoordinates(A_Command.getSet());


    }

}
