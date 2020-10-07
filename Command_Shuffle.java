package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Shuffle extends A_Command implements Command, Serializable {


    public Command_Shuffle( ){

    }
    @Override
    public ArrayList<String> execute() {
      return   Receiver.shuffle(A_Command.getSet());


    }

}
