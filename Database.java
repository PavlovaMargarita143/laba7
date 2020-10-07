package server;

import com.company.*;

import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;
import org.postgresql.*;


public class Database {
    String url;
    String user;
    String password;
    String driverPath;
    static Connection connection;

    public Database(String url, String user, String password, String driverPath) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driverPath = driverPath;
    }

    public void downloadDriver() {
        try {
            Class.forName(this.driverPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public void connectionToDatabase() throws SQLException {
        this.downloadDriver();
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed : " + e.getMessage());
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        this.createType();
        createSeq();
        this.createTable();
    }

    public ArrayList<String> AuthorizationUser(User user) throws SQLException {
        if (checkLogin(user)) {
            if (checkPassword(user)) {
                return new ArrayList<>(Collections.singleton("Successful authorization " + user.getLogin()));
            } else return new ArrayList<>(Collections.singleton("Wrong password"));
        }
        return new ArrayList<>(Collections.singleton("There is no user with this nickname"));
    }

    private boolean checkLogin(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
        while (resultSet.next()) {
            String login = resultSet.getString(1);
            System.out.println("1:" + login + " 2:" + user.getLogin());
            if (user.getLogin().equals(login)) return true;
        }
        return false;
    }

    private synchronized boolean checkPassword(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
        while (resultSet.next()) {
            String login = resultSet.getString(1);
            if (user.getLogin().equals(login)) {
                return Hashcode.crypt(user, resultSet.getString(3)).equals(resultSet.getString(2));
            }
        }
        return false;
    }

    public synchronized ArrayList<String> registerUser(User user) throws SQLException {
        if (!checkLogin(user)) {
            String hashedPassword = Hashcode.crypt(user);
            System.out.println("Ник:" + user.getLogin() + " Пароль:" + user.getPassword());
            String command = "INSERT INTO Users VALUES ('" + user.getLogin() + "','" + hashedPassword
                    + "','" + user.getSalt() + "');";
            executor(command);
            return new ArrayList<>(Collections.singleton("Successful registration " + user.getLogin()));
        }
        return new ArrayList<>(Collections.singleton("Пользователь с данным логином существует."));
    }


    public synchronized static void executor(String command) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            if (stmt != null) {
                stmt.executeUpdate(command);
            }
        } catch (SQLException ignored) {
        }
    }


    public void createType() {
        executor("CREATE TYPE human  AS(age integer);");
        executor(" CREATE TYPE coordinates AS( x integer,y integer);");
        executor("CREATE TYPE  standardOfLiving AS ENUM" +
                " ('ULTRA_HIGH', 'HIGH', 'LOW', 'ULTRA_LOW','NIGHTMARE');");
        executor("CREATE TYPE government AS ENUM" + "(  'OLIGARCHY','THALASSOCRACY','JUNTA','ETHNOCRACY');");
    }

    public static void createTable() {
        executor("CREATE TABLE City" +
                "(ID serial primary key,name text,coordinates text," +
                "creationdate timestamp DEFAULT NOW(),area double precision, " +
                "population integer,metersAboveSeaLevel double precision,agglomeration double precision, " +
                "government text, governer text, standardOfLiving text, owner text);");
        executor("CREATE TABLE Users" + "(login text," + " password text," + "salt text);");
    }

    public void createSeq() {
        executor(" CREATE SEQUENCE iterator " + "start 1" + " increment 1 " + " NO MAXVALUE " + " CACHE 1;");
    }

    public static void readData() throws SQLException {
        ArrayList<City> city= new ArrayList<>();
        Statement statement = connection.createStatement();
        createTable();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM City");
        City newCity;
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String coordinates_str = resultSet.getString(3);
            String substring = coordinates_str.substring(coordinates_str.indexOf("(") + 1,
                    coordinates_str.indexOf(","));
            String substring1 = coordinates_str.substring(coordinates_str.indexOf(",") + 1,
                    coordinates_str.indexOf(")"));
            Coordinates coordinates = new Coordinates(Long.parseLong(substring), Integer.parseInt(substring1));
            Timestamp timestamp = resultSet.getTimestamp(4);
            double area = resultSet.getDouble(5);
            int population = resultSet.getInt(6);
            double metersAboveSeaLevel = resultSet.getDouble(7);
            float agglomiration = resultSet.getFloat(8);
            String government_s = resultSet.getString(9);
            Government government = Government.valueOf(government_s.trim());
            String governer_s = resultSet.getString(10);
            Human governer = null;
            if (governer_s != null && !governer_s.isEmpty()){
                governer=new Human(Integer.parseInt(governer_s.substring(coordinates_str.indexOf("(") + 1,
                        governer_s.indexOf(")"))));
            }
            String sOfliv_s = resultSet.getString(11);
            StandardOfLiving standardOfLiving = null;
            if (sOfliv_s != null && !sOfliv_s.isEmpty()&&!sOfliv_s.equals("null")) standardOfLiving = StandardOfLiving.valueOf(sOfliv_s.trim());
            String owner = resultSet.getString(12);
            if (governer != null && standardOfLiving != null)
                newCity = new City(name,coordinates,area,population,metersAboveSeaLevel,agglomiration,government, standardOfLiving, governer);
            else if (governer != null)newCity = new City(name,coordinates,area,population,metersAboveSeaLevel,agglomiration,government, governer);
            else newCity = new City(name,coordinates,area,population,metersAboveSeaLevel,agglomiration,government);
            newCity.setid(id);
            newCity.setOwner(owner);
            city.add(newCity);
        }

        A_Command.setSet(city);
    }
}
