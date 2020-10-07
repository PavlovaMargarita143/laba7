package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Command_AverageOfAgglomeration extends A_Command implements  Command, Serializable {
    public Command_AverageOfAgglomeration() {
    }

    @Override
    public ArrayList<String> execute() {
        return Receiver.averageOfAgg(A_Command.getSet());

    }
}