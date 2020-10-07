package server;

import com.company.A_Command;
import com.company.Command;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_Disconnect extends A_Command implements Command , Serializable {
    public  Command_Disconnect(){}

    @Override
    public ArrayList<String> execute() throws SQLException {
        return Database_Reciver.Disconnect();
    }


}
