package com.company;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Command_Save extends A_Command implements Command, Serializable {


    public Command_Save( ){

    }
    @Override
    public ArrayList<String> execute() {
        try {
            Receiver.save(A_Command.getSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
