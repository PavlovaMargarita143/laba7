package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_Error extends A_Command implements Command, Serializable {
    public Command_Error(){}
    @Override
    public ArrayList<String> execute(){
       return Receiver.error();

    }

}
