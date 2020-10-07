package com.company;



import com.google.gson.Gson;
import server.Database;
import server.User;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Receiver implements  Serializable{

    public synchronized static ArrayList<String> add(City newCity, ArrayList<City> collection, String login) throws SQLException {
      collection.add(newCity);
      ArrayList<String> s = new ArrayList<>();
      s.add("элемент успешно добавлен");
      String sql = "INSERT INTO City (ID ,name, coordinates,creationdate,area , population, metersAboveSeaLevel ,agglomeration, government , governer , standardOfLiving, owner)"+
              "Values (nextval('iterator'), ?,?, Now(), ?, ?, ?, ?,?, ?, ?,?)";
        PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, newCity.getName());
        preparedStatement.setString(2,"(" + newCity.getCoordinates().getX() + "," + newCity.getCoordinates().getY() + ")");
        preparedStatement.setDouble(3,newCity.getArea());
        preparedStatement.setInt(4,newCity.getPopulation());
        preparedStatement.setDouble(5,newCity.getMetersAboveSeaLevel());
        preparedStatement.setFloat(6,newCity.getAgglomeration());
        preparedStatement.setString(7,String.valueOf(newCity.getGovernment()));
        if(newCity.getGovernor()!=null) preparedStatement.setString(8, "(" +String.valueOf(newCity.getGovernor().getAge())+ ")");
        else  preparedStatement.setString(8, null);
        if(newCity.getStandardOfLiving()!=null) preparedStatement.setString(9, String.valueOf(newCity.getStandardOfLiving()));
        else preparedStatement.setString(9, null);
        preparedStatement.setString(10,login);
        preparedStatement.execute();
        Database.readData();
      return s ;
    }
    public synchronized   static ArrayList<String> remove(ArrayList<City> collection, int index, String login) throws SQLException {
        ArrayList<String> s = new ArrayList<>();
        if (A_Command.getSet().size() == 0) {
           s.add("Коллекция пуста, вы не можетe удалить элементы");}
        else if  (index >= A_Command.getSet().size()){
            s.add("ОШИБКА!! Элемента с таким индексом не существует,чтобы удалить элемент число должно быть НЕ меньше нуля и МЕНЬШЕ чем " + A_Command.getSet().size());
        }
        else if (collection.get(index).getOwner().compareTo(login)!=0){s.add("элемент вам не принадлежит");}
       else {collection.remove(index);
       s.add("элемент успешно удален");
       Database.executor("DELETE FROM City WHERE ID = "+ collection.get(index).getId() +";");
       Database.readData();
       }
        return s;
    }

    public static ArrayList<String> help(){
        ArrayList<String> s = new ArrayList<>();
        s.add ("Вы можете использовать следующие команды:" +"\n"+
                "RegisterUser: регистрация в БД"+ "\n" +
                "Authorization: вход "
        );
        s.add ("Info : вывести в стандартный поток вывода информацию о коллекции ");
        s.add ("Show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        s.add ("Add : добавить новый элемент в коллекцию");
        s.add ("UpdateId : обновить значение элемента коллекции, id которого равен заданному");
       s.add ("RemoveById : удалить элемент из коллекции по его id");
        s.add ("Clear : очистить коллекцию");
        s.add ("Script : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
       s.add ("Exit : завершить программу ");
       s.add ("RemoveAt : удалить элемент, находящийся в заданной позиции коллекции (index)");
        s.add ("Shuffle : перемешать элементы коллекции в случайном порядке");
        s.add ("Sort : отсортировать коллекцию в естественном порядке");
       s.add ("SumOfSeaLevel : вывести сумму значений поля metersAboveSeaLevel для всех элементов коллекции");
        s.add (" AverageOfAgg : вывести среднее значение поля agglomeration для всех элементов коллекции");
       s.add (" MaxCoordinates : вывести любой объект из коллекции, значение поля coordinates которого является максимальным");
       return s;

    }
    public synchronized static ArrayList<String> clear(ArrayList<City> collection, String login) throws SQLException {
        Database.executor("DELETE FROM City WHERE owner ='" + login + "';");
        Database.readData();
        ArrayList<String> s = new ArrayList<>();
        s.add(" Ваши элементы коллекции успешно удалены");
        return s;

    }
    public  static ArrayList<String> error(){
        ArrayList<String> s = new ArrayList<>();
        s.add("Вы неправильно ввели команду, попробуйте еще раз");
        return s;


    }
    public static ArrayList<String> exit(){
        ArrayList<String> s = new ArrayList<>();
        s.add("Программа завершена");
        System.exit(0);
        return s;

    }
    public synchronized static ArrayList<String> show(ArrayList<City> collection){
        ArrayList<String> s = new ArrayList<>();
        if (collection.size()>0){
        s.add(collection.toString());}
        else s.add("Коллекция пуста!!");
        return s;
    }
    public synchronized static ArrayList<String> shuffle(ArrayList<City> collection){
        Collections.shuffle(collection);
        ArrayList<String> s = new ArrayList<>();
        s.add("вы перемешали эллементы коллекции");
        return s;
    }
    public synchronized static ArrayList<String> sort(ArrayList<City> collection){
        ArrayList<String> s = new ArrayList<>();
        collection.sort(City::compareTo);
        s.add("коллекция успешно отсортирована");
        return s;
    }
    public synchronized static ArrayList<String> removeById (ArrayList<City> collection, int id, String login) throws SQLException {
        int b =1;
        ArrayList<String> s = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++){
            b++;

            if ((Objects.equals(Integer.valueOf(collection.get(i).getId()), id))&&(login.compareTo(collection.get(i).getOwner())==0)){
                collection.remove(i);
                s.add("Элемент успешно удален");
                b =0;
                Database.executor("DELETE FROM City THERE ID= "+ id +" AND owner ='" + login + "';");
                Database.readData();
            }

    }if (b>collection.size()) {s.add("Элемент с таким ID не найден или вам не принадлежит");}

        return s;
    }
    public synchronized static ArrayList<String> update(ArrayList<City> collection, int id, City newCity, String login ) throws SQLException {
        int b =1;
        ArrayList<String> s = new ArrayList<>();
                String sql = "UPDATE City SET  name = ?, coordinates= ?,creationdate= Now(),area = ?, population = ?, metersAboveSeaLevel= ? ,agglomeration= ?, government= ? , governer= ? , standardOfLiving= ? WHERE ID=" + id+ " AND owner ='"+ login+"'";
                PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, newCity.getName());
                preparedStatement.setString(2,"(" + newCity.getCoordinates().getX() + "," + newCity.getCoordinates().getY() + ")");
                preparedStatement.setDouble(3,newCity.getArea());
                preparedStatement.setInt(4,newCity.getPopulation());
                preparedStatement.setDouble(5,newCity.getMetersAboveSeaLevel());
                preparedStatement.setFloat(6,newCity.getAgglomeration());
                preparedStatement.setString(7,String.valueOf(newCity.getGovernment()));
                if(newCity.getGovernor()!=null) preparedStatement.setString(8, "(" +String.valueOf(newCity.getGovernor().getAge())+ ")");
                else  preparedStatement.setString(8, null);
                if(newCity.getStandardOfLiving()!=null) preparedStatement.setString(9, String.valueOf(newCity.getStandardOfLiving()));
                else preparedStatement.setString(9, null);
                preparedStatement.execute();
                Database.readData();

        for (int i = 0; i < collection.size(); i++){
            b++;
            if (Objects.equals(Integer.valueOf(collection.get(i).getId()), id)&&collection.get(i).getOwner().compareTo(login)==0){
                s.add("Элемент успешно обновлен");
                b =0;}}
        if (b>collection.size()) {s.add("Элемент с таким ID не найден или вам не принадлежит");}
        return s;
    }
    public synchronized static ArrayList<String> sumOfSeaL(ArrayList<City> collection){
        Double S;
        S =  0.0;

        ArrayList<String> s = new ArrayList<>();
       for (int i = 0; i < collection.size(); i++){
           S = S + collection.get(i).getMetersAboveSeaLevel();
        }
        s.add( "суммa значений поля metersAboveSeaLevel " +S.toString()+ " ");
        return s;
    }
    public synchronized static ArrayList<String> averageOfAgg(ArrayList<City> collection){
        Float A;
        A = Float.valueOf(0);
        ArrayList<String> s = new ArrayList<>();
        if (A_Command.getSet().size() == 0) {
            s.add(" 0 ");

        }
        else {
        for (int i = 0; i < collection.size(); i++){
            A = A + collection.get(i).getAgglomeration();
        }
        A= A/collection.size();

        s.add("среднее значение поля agglomeration "+ A.toString()+ " ");}
        return s;
    }
    public synchronized static ArrayList<String> info(ArrayList<City> collection){
        ArrayList<String> s = new ArrayList<>();
        s.add ("коллекция: " + collection.getClass());
        s.add("Количество элемeнтов: " + collection.size());
        s.add (collection.toString());
        return s;
    }
public static void save(ArrayList<City> collection) throws IOException {
    Gson gson = new Gson();
    FileOutputStream file=new FileOutputStream("output.json");
    String b = "";
    String c = ""+'\n' ;
if (collection.size()==0){
    file.write(b.getBytes());
    file.close();
}
   else {for (int i=0;i<collection.size()-1; i++){
    String json = gson.toJson(collection.get(i));
    file.write(json.getBytes());
    file.write(c.getBytes());
   }
   String json = gson.toJson(collection.get(collection.size()-1));
   file.write(json.getBytes());

    file.close();
}}
public synchronized static ArrayList<String> maxCoordinates(ArrayList<City> collection){
        Double C; int j =-1;
        C= -0.1;
    ArrayList<String> s = new ArrayList<>();
    if (A_Command.getSet().size() == 0) {
        s.add("коллекция пуста, элемент с максимальным значением поля координаты не может быть найден");
    }
 else{   for (int i = 0; i < collection.size(); i++){
      if (Math.sqrt( collection.get(i).getCoordinates().getX()*collection.get(i).getCoordinates().getX()+ collection.get(i).getCoordinates().getY()*collection.get(i).getCoordinates().getY())> C) {
          C=Math.sqrt( collection.get(i).getCoordinates().getX()*collection.get(i).getCoordinates().getX()+ collection.get(i).getCoordinates().getY()*collection.get(i).getCoordinates().getY());
          j=i;
        }
      } s.add(collection.get(j).toString());
 }
return s;

    }
    public static ArrayList<String> script (File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        ArrayList<String> s = new ArrayList<>();
        s.add("Считываем команды из файла...");
        return s;

        //создаем BufferedReader с существующего FileReader для построчного считывания
        }


    public static ArrayList<String> registerUser(User user, Database database) throws SQLException {
       return database.registerUser(user);
    }








}
