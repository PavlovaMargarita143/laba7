package server;

import com.company.A_Command;
import com.company.Command;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Command_Authorization extends A_Command implements Command , Serializable {
    User user;

    public Command_Authorization(User user) {
        this.user = user;
    }
    public Command_Authorization() {

    }

    @Override
    public ArrayList<String> execute() throws SQLException {
        try {
            return Database_Reciver.AuthorizationUser(this.user, Server.getDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<String>(Collections.singleton(e.getMessage()));
        }
    }


}
