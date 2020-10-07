package com.company;

import server.Command_Authorization;
import server.User;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Reader implements  Serializable{


    //public Reader(){}
    public Command ReadCommand() {
        System.out.println("Введите команду, чтобы посмотреть доступные введите help");
        Command command = null;
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        ArrayList In = new ArrayList();
        In.add(input);
        for (int i=0; i<In.size();i++) {
            System.out.println("Отправляем на обработку команду " + input);
            if ((input.compareTo("Script") == 0) || (input.compareTo("script") == 0)) {
                System.out.println("Введите название файла");
                String file = scanner.next();
                command = new Command_Script(new File(file));


            } else if ((input.compareTo("Help") == 0) || (input.compareTo("help") == 0)) {
               command= new Command_Help();
            } else if ((input.compareTo("Exit") == 0) || (input.compareTo("exit") == 0)) {
                command = new Command_Exit();
            } else if ((input.compareTo("Show") == 0) || (input.compareTo("show") == 0)) {
                command = new Command_Show();
            } else if ((input.compareTo("Sort") == 0) || (input.compareTo("sort") == 0)) {
                command = new Command_Sort();
            } else if ((input.compareTo("Clear") == 0) || (input.compareTo("clear") == 0)) {
                command = new Command_Clear();
            } else if ((input.compareTo("Shuffle") == 0) || (input.compareTo("shuffle") == 0)) {
                command = new Command_Shuffle();
            } else if ((input.compareTo("RegisterUser") == 0) || (input.compareTo("registerUser") == 0) || (input.compareTo("registeruser") == 0)){
                command = new Command_RegisterUser(MakeUser());
            }
            else if ((input.compareTo("Authorization") == 0) || (input.compareTo("authorization") == 0)){
                command = new Command_Authorization(MakeUser());
            }
            else if ((input.compareTo("RemoveAt") == 0) || (input.compareTo("removeat") == 0) || (input.compareTo("removeAt") == 0)) {

                System.out.println("Введите индекс элемента, который хотите удалить");
                String ind = null;
                while (true) {
                    ind = scanner.nextLine();
                    boolean result = ind.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное целое число!");
                    } else break;
                }
                Integer index = Integer.parseInt(ind);
                command = new Command_RemoveAt(index);
            } else if ((input.compareTo("Add") == 0) || (input.compareTo("add") == 0)) {
                System.out.println("Введите название города ");
                String name = scanner.nextLine();
                System.out.println("Введите координаты x затем y");
                String x1 = null;
                System.out.println("Введите x");
                while (true) {
                    x1 = scanner.nextLine();
                    boolean result = x1.matches("-?\\d+?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите целое число!");
                    } else break;
                }
                long x = Long.parseLong(x1);
                System.out.println("Введите y");
                String y1 = null;
                while (true) {
                    y1 = scanner.nextLine();
                    boolean result = y1.matches("-?\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!!Введите целое число!");
                    } else break;
                }
                int y = Integer.parseInt(y1);

                System.out.println("Введите площадь");
                String y2 = null;
                while (true) {
                    y2 = scanner.nextLine();
                    boolean result = y2.matches("\\d+(\\.\\d+)?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное число формата 12.12!");
                    } else break;
                }
                Double area = Double.parseDouble(y2);
                System.out.println("Введите количество населения");
                String y3 = null;
                while (true) {
                    y3 = scanner.nextLine();
                    boolean result = y3.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное целое число!");
                    } else break;
                }
                int pop = Integer.parseInt(y3);

                System.out.println("Введите высоту над уровнем моря, в метрах");
                String y4 = null;
                while (true) {
                    y4 = scanner.nextLine();
                    boolean result = y4.matches("-?\\d+(\\.\\d+)?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите число формата 12.12!");
                    } else break;
                }
                Double SL = Double.parseDouble(y4);
                System.out.println("Введите аггломирацию");
                String y5 = null;
                while (true) {
                    y5 = scanner.nextLine();
                    boolean result = y5.matches("\\d+(\\.\\d+)?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное число в формате 12.12!");
                    } else break;
                }
                System.out.println("Введите возраст губернатора");
                String y9 = null;
                while (true) {
                    y9 = scanner.nextLine();
                    if (y9.compareTo("") == 0) {
                        break;
                    }
                    boolean result = y9.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное целое число!");
                    } else if ((result == true) && ((Integer.parseInt(y9) > 80) || (Integer.parseInt(y9) < 25))) {
                        System.out.println("ОШИБКА!! Возраст губернатора не может быть больше 80 или меньше 25, введите другое число");
                    } else break;
                }

                Float agg = Float.parseFloat(y5);
                System.out.println("Введите систему правления из предложенных :THALASSOCRARY, JUNTA, ETHNOCRACY, OLIGARCHY");
                String g1 = null;
                while (true) {
                    g1 = scanner.nextLine();
                    if ((g1.contains("THALASSOCRARY") || g1.contains("JUNTA") || g1.contains("ETHNOCRACY") || g1.contains("OLIGARCHY")) == true) {
                        break;
                    } else
                        System.out.println("ОШИБКА!! Выберете слово из предложенных и введите его: THALASSOCRARY, JUNTA, ETHNOCRACY, OLIGARCHY");
                }
                String g = g1;
                System.out.println("Введите уровень жизни из предложенных  :ULTRA_HIGH, HIGH, LOW, ULTRA_LOW,NIGHTMARE");
                String s1 = null;
                while (true) {
                    s1 = scanner.nextLine();
                    if (s1.compareTo("") == 0) {
                        break;
                    }
                    if ((s1.contains("ULTRA_HIGH") || s1.contains("HIGH") || s1.contains("LOW") || s1.contains("ULTRA_LOW") || s1.contains("NIGHTMARE")) == true) {
                        break;
                    } else
                        System.out.println("ОШИБКА!! Выберете слово из предложенных и введите его: ULTRA_HIGH, HIGH, LOW, ULTRA_LOW,NIGHTMARE");
                }
                String s = s1;
                if ((y9.compareTo("") == 0) && (s.compareTo("") == 0)) {
                    command = new Command_Add(new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim())));
                } else if ((y9.compareTo("") == 0) && (s.compareTo("") != 0)) {
                    command = new Command_Add(new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim()), StandardOfLiving.valueOf(s.trim())));
                } else if ((s.compareTo("") == 0) && (y9.compareTo("") != 0)) {
                    int age = Integer.parseInt(y9);
                    command = new Command_Add(new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim()), new Human(age)));
                } else {
                    int age = Integer.parseInt(y9);
                    command = new Command_Add(new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim()), StandardOfLiving.valueOf(s.trim()), new Human(age)));
                }
            } else if ((input.compareTo("RemoveById") == 0) || (input.compareTo("removebyid") == 0) || (input.compareTo("removeById") == 0)) {
                System.out.println("Введите ID элемента, который хотите удалить");
                String id1 = null;
                while (true) {
                    id1 = scanner.next();
                    boolean result = id1.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное целое число!");
                    } else break;
                }
                int id = Integer.parseInt(id1);

                command = new Command_RemoveById(id);

            } else if ((input.compareTo("UpdateId") == 0) || (input.compareTo("updateid") == 0) || (input.compareTo("updateId") == 0)) {
                System.out.println("Введите id элемента который хотите обновить");
                String id1 = null;
                while (true) {
                    id1 = scanner.nextLine();
                    boolean result = id1.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите число!");
                    } else break;
                }
                int id = Integer.parseInt(id1);

                System.out.println("Введите новый объект коллекции, Введите название города ");
                String name = scanner.nextLine();
                System.out.println("Введите координаты x затем y");
                String x1 = null;
                System.out.println("Введите x");
                while (true) {
                    x1 = scanner.nextLine();
                    boolean result = x1.matches("-?\\d+?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите целое число!");
                    } else break;
                }
                long x = Long.parseLong(x1);
                System.out.println("Введите y");
                String y1 = null;
                while (true) {
                    y1 = scanner.nextLine();
                    boolean result = y1.matches("-?\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!!Введите целое число!");
                    } else break;
                }
                int y = Integer.parseInt(y1);

                System.out.println("Введите площадь");
                String y2 = null;
                while (true) {
                    y2 = scanner.nextLine();
                    boolean result = y2.matches("\\d+(\\.\\d+)?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное число формата 12.12!");
                    } else break;
                }
                Double area = Double.parseDouble(y2);
                System.out.println("Введите количество населения");
                String y3 = null;
                while (true) {
                    y3 = scanner.nextLine();
                    boolean result = y3.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное целое число!");
                    } else break;
                }
                int pop = Integer.parseInt(y3);

                System.out.println("Введите высоту над уровнем моря, в метрах");
                String y4 = null;
                while (true) {
                    y4 = scanner.nextLine();
                    boolean result = y4.matches("-?\\d+(\\.\\d+)?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите число формата 12.12!");
                    } else break;
                }
                Double SL = Double.parseDouble(y4);
                System.out.println("Введите аггломирацию");
                String y5 = null;
                while (true) {
                    y5 = scanner.nextLine();
                    boolean result = y5.matches("\\d+(\\.\\d+)?");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное число в формате 12.12!");
                    } else break;
                }
                System.out.println("Введите возраст губернатора");
                String y9 = null;
                while (true) {
                    y9 = scanner.nextLine();
                    if (y9.compareTo("") == 0) {
                        break;
                    }
                    boolean result = y9.matches("\\d+");
                    if (result == false) {
                        System.out.println("ОШИБКА!! Введите положительное целое число!");
                    } else if ((result == true) && ((Integer.parseInt(y9) > 80) || (Integer.parseInt(y9) < 25))) {
                        System.out.println("ОШИБКА!! Возраст губернатора не может быть больше 80 или меньше 25, введите другое число");
                    } else break;
                }

                Float agg = Float.parseFloat(y5);
                System.out.println("Введите систему правления из предложенных :THALASSOCRARY, JUNTA, ETNOCRACY, OLIGARCHY");
                String g1 = null;
                while (true) {
                    g1 = scanner.nextLine();
                    if ((g1.contains("THALASSOCRARY") || g1.contains("JUNTA") || g1.contains("ETHNOCRACY") || g1.contains("OLIGARCHY")) == true) {
                        break;
                    } else
                        System.out.println("ОШИБКА!! Выберете слово из предложенных и введите его: THALASSOCRARY, JUNTA, ETHNOCRACY, OLIGARCHY");
                }
                String g = g1;
                System.out.println("Введите уровень жизни из предложенных  :ULTRA_HIGH, HIGH, LOW, ULTRA_LOW,NIGHTMARE");
                String s1 = null;
                while (true) {
                    s1 = scanner.nextLine();
                    if (s1.compareTo("") == 0) {
                        break;
                    }
                    if ((s1.contains("ULTRA_HIGH") || s1.contains("HIGH") || s1.contains("LOW") || s1.contains("ULTRA_LOW") || s1.contains("NIGHTMARE")) == true) {
                        break;
                    } else
                        System.out.println("ОШИБКА!! Выберете слово из предложенных и введите его: ULTRA_HIGH, HIGH, LOW, ULTRA_LOW,NIGHTMARE");
                }
                String s = s1;
                if ((y9.compareTo("") == 0) && (s.compareTo("") == 0)) {
                    command = new Command_UpdateId(id, new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim())));
                } else if ((y9.compareTo("") == 0) && (s.compareTo("") != 0)) {
                    command = new Command_UpdateId(id, new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim()), StandardOfLiving.valueOf(s.trim())));
                } else if ((s.compareTo("") == 0) && (y9.compareTo("") != 0)) {
                    int age = Integer.parseInt(y9);
                    command = new Command_UpdateId(id, new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim()), new Human(age)));
                } else {
                    int age = Integer.parseInt(y9);
                    command = new Command_UpdateId(id, new City(name, new Coordinates(x, y), (Double) area, pop, SL, agg, Government.valueOf(g.trim()), StandardOfLiving.valueOf(s.trim()), new Human(age)));
                }
            } else if ((input.compareTo("SumOfSeaLevel") == 0) || (input.compareTo("sumofsealevel") == 0) || (input.compareTo("sumOfSeaLevel") == 0)) {
                command = new Command_SumOfSeaLevel();
            } else if ((input.compareTo("AverageOfAgg") == 0) || (input.compareTo("averageofagg") == 0) || (input.compareTo("averageOfAgg") == 0)) {

                command = new Command_AverageOfAgglomeration();
            } else if ((input.compareTo("Info") == 0) || (input.compareTo("info") == 0)) {
                command = new Command_Info();
            } else if ((input.compareTo("Save") == 0) || (input.compareTo("save") == 0)) {
                command = new Command_Save();
            } else if ((input.compareTo("MaxCoordinates") == 0) || (input.compareTo("maxcoordinates") == 0) || (input.compareTo("maxCoordinates") == 0)) {

                command = new Command_MaxCoordinates();
            } else {
                command = new Command_Error();
            }
        }
        // command.execute();

           return command;
}
    private static User MakeUser() {
        String login;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Для выполнения команды необходимо указать логин и пароль.");
        System.out.print("Введите логин:");
        login = scanner.nextLine().trim();
        while (login.isEmpty()) {
            System.out.print("Вы ввели логин, не удовлетворяющий требованиям. Он должен быть больше 4 символов.");
            System.out.print("Введите логин:");
            login = scanner.nextLine().trim();
        }
        System.out.print("Введите пароль:");
        password = scanner.nextLine().trim();
        while (password.isEmpty()) {
            System.out.print("Вы ввели пароль, не удовлетворяющий требованиям. Он должен быть больше 4 символов.");
            System.out.print("Введите пароль:");
            password = scanner.nextLine().trim();
        }

        return new User(login, password);
    }
}
