package com.company;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Command_Script extends A_Command implements  Command, Serializable {
    public File file;

    public Command_Script(File file){
        this.file = file;
    }
    int k = 0;
    @Override
    public ArrayList<String> execute() throws SQLException {
        /*try {
            Reciver.script(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Command command=null;
        ArrayList<String> In = new ArrayList();
        ArrayList<String> s = new ArrayList<>();
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            s.add("Такого файла не существует!!!");
        }
        BufferedReader reader = new BufferedReader(fr);
        // считаем сначала первую строку
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            String a = line;

            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] w = a.split(" ");
            s.add("Из скрипта получена команда: " + w[0]);

            if (w[0].compareTo("RemoveAt") == 0) {

                if (w.length < 2) {
                    s.add("Не хватает данных для выполнения команды RemoveAt, пожалуйста, исправьте данные файла");
                    continue;
                }


                    boolean result = w[1].matches("\\d+");
                    if (result == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!! Введите положительное целое число!");


                    } else if ((result == true) && (Integer.parseInt(w[1]) >= A_Command.getSet().size())) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Элемента с таким индексом не существует, введите число МЕНЬШЕ чем " + A_Command.getSet().size());

                    }


               else{ int ind = Integer.parseInt(w[1]);
                command = new Command_RemoveAt(ind);}



            } else if (w[0].compareTo("RemoveById") == 0) {
                if (w.length < 2) {
                   s.add("Не хватает данных для выполнения команды RemoveById, пожалуйста, исправьте данные файла");
                  continue;
                }

                    boolean result = w[1].matches("\\d+");
                    if (result == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число!");


                    }

               else{ int ind = Integer.parseInt(w[1]);
                command = new Command_RemoveById(ind);}
                //command.execute();
            } else if (w[0].compareTo("Add") == 0) {
                if (w.length < 11) {
                    s.add("Не хватает данных для выполнения команды Add, пожалуйста, исправьте данные файла, строка должна содержать минимум 7 значений после названия команды");
                    continue;
                }

                    boolean result = w[2].matches("\\d+");
                    if (result == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для х!");

                    }


                    boolean result1 = w[3].matches("\\d+");
                    if (result1 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для у!");

                    }


                    boolean result2 = w[4].matches("\\d+");
                    if (result2 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для площади!");


                    }


                    boolean result3 = w[5].matches("\\d+");
                    if (result3 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для количества населения!");


                    }

                    boolean result4 = w[6].matches("\\d+");
                    if (result4 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число высоты над уровнем моря !");


                    }

                    boolean result5 = w[7].matches("\\d+");
                    if (result5 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для аггломерации!");


                    }
                while (true) {

                    if (w[10].compareTo("-") == 0) {
                        break;
                    }
                    boolean result6 = w[10].matches("\\d+");
                    if (result6 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число!");
                       continue;
                    } else if ((result6 == true) && ((Integer.parseInt(w[10]) > 80) || (Integer.parseInt(w[10]) < 25))) {
                        s.add("ОШИБКА!! Возраст губернатора не может быть больше 80 или меньше 25, введите другое число");
                        continue;
                    } else break;
                }

               while (true){

                    if ((w[8].contains("THALASSOCRARY") || w[8].contains("JUNTA") || w[8].contains("ETNOCRACY") || w[8].contains("OLIGARCHY")) == true) {
                        break;
                    } else {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Выберете слово из предложенных и введите его: THALASSOCRARY, JUNTA, ETNOCRACY, OLIGARCHY");
                        continue;
                    }
                }


                while (true) {

                    if (w[9].compareTo("-") == 0) {
                        break;
                    }
                    if ((w[9].contains("ULTRA_HIGH") || w[9].contains("HIGH") || w[9].contains("LOW") || w[9].contains("ULTRA_LOW") || w[9].contains("NIGHTMARE")) == true) {
                        break;
                    } else {
                        s.add("ОШИБКА!! Выберете слово из предложенных и введите его: ULTRA_HIGH, HIGH, LOW, ULTRA_LOW,NIGHTMARE");
                        continue;
                    }
                }


                    if ((w[9].compareTo("-") == 0) && (w[10].compareTo("-") == 0)) {
                        command = new Command_Add(new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                                Float.parseFloat(w[7]), Government.valueOf(w[8].trim())));
                    } else if ((w[9].compareTo("-") == 0) && (w[10].compareTo("-") != 0)) {
                        command = new Command_Add(new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                                Float.parseFloat(w[7]), Government.valueOf(w[8].trim()), StandardOfLiving.valueOf(w[9].trim())));
                    } else if ((w[9].compareTo("-") != 0) && (w[10].compareTo("-") == 0)) {
                        command = new Command_Add(new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                                Float.parseFloat(w[7]), Government.valueOf(w[8].trim()), new Human(Integer.parseInt(w[10]))));
                    } else
                        command = new Command_Add(new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                                Float.parseFloat(w[7]), Government.valueOf(w[8].trim()), StandardOfLiving.valueOf(w[9].trim()), new Human(Integer.parseInt(w[10]))));

                //command.execute();
                // System.out.println("элемент успешно добавлен");
            } else if (w[0].compareTo("UpdateId") == 0) {
                if (w.length < 12) {
                    s.add("Не хватает данных для выполнения команды UpdateId, пожалуйста, исправьте данные файла, строка должна содержать минимум 11 значений после названия команды, последнее число является ID обновляемого элемента");
                    continue;
                }
                boolean result = w[2].matches("\\d+");
                if (result == false) {
                    s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для х!");
                    continue;

                }


                boolean result1 = w[3].matches("\\d+");
                if (result1 == false) {
                    s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для у!");
                    continue;

                }


                boolean result2 = w[4].matches("\\d+");
                if (result2 == false) {
                    s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для площади!");
                    continue;

                }


                boolean result3 = w[5].matches("\\d+");
                if (result3 == false) {
                    s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для количества населения!");
                    continue;

                }

                boolean result4 = w[6].matches("\\d+");
                if (result4 == false) {
                    s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число высоты над уровнем моря !");
                    continue;

                }

                boolean result5 = w[7].matches("\\d+");
                if (result5 == false) {
                    s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число для аггломерации!");
                    continue;

                }
                while (true) {

                    if (w[10].compareTo("-") == 0) {
                        break;
                    }
                    boolean result6 = w[10].matches("\\d+");
                    if (result6 == false) {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Введите положительное целое число!");
                        continue;
                    } else if ((result6 == true) && ((Integer.parseInt(w[10]) > 80) || (Integer.parseInt(w[10]) < 25))) {
                        s.add("ОШИБКА!! Возраст губернатора не может быть больше 80 или меньше 25, введите другое число");
                        continue;
                    } else break;
                }

                while (true) {

                    if ((w[8].contains("THALASSOCRARY") || w[8].contains("JUNTA") || w[8].contains("ETNOCRACY") || w[8].contains("OLIGARCHY")) == true) {
                        break;
                    } else {
                        s.add("ОШИБКА ДАННЫХ ФАЙЛА!!! Выберете слово из предложенных и введите его: THALASSOCRARY, JUNTA, ETNOCRACY, OLIGARCHY");
                        continue;
                    }
                }


                while (true) {

                    if (w[9].compareTo("-") == 0) {
                        break;
                    }
                    if ((w[9].contains("ULTRA_HIGH") || w[9].contains("HIGH") || w[9].contains("LOW") || w[9].contains("ULTRA_LOW") || w[9].contains("NIGHTMARE")) == true) {
                        break;
                    } else {
                        s.add("ОШИБКА!! Выберете слово из предложенных и введите его: ULTRA_HIGH, HIGH, LOW, ULTRA_LOW,NIGHTMARE");
                        continue;
                    }
                }

                if ((w[9].compareTo("-") == 0) && (w[10].compareTo("-") == 0)) {
                    command = new Command_UpdateId(Integer.parseInt(w[11]), new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                            Float.parseFloat(w[7]), Government.valueOf(w[8].trim())));
                } else if ((w[9].compareTo("-") == 0) && (w[10].compareTo("-") != 0)) {
                    command = new Command_UpdateId(Integer.parseInt(w[11]), new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                            Float.parseFloat(w[7]), Government.valueOf(w[8].trim()),  new Human(Integer.parseInt(w[10]))));
                } else if ((w[9].compareTo("-") != 0) && (w[10].compareTo("-") == 0)) {
                    command = new Command_UpdateId(Integer.parseInt(w[11]), new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                            Float.parseFloat(w[7]), Government.valueOf(w[8].trim()),StandardOfLiving.valueOf(w[9].trim())));
                } else
                    command = new Command_UpdateId(Integer.parseInt(w[11]), new City(w[1], new Coordinates(Long.parseLong(w[2]), Integer.parseInt(w[3])), Double.parseDouble(w[4]), Integer.parseInt(w[5]), Double.parseDouble(w[6]),
                            Float.parseFloat(w[7]), Government.valueOf(w[8].trim()), StandardOfLiving.valueOf(w[9].trim()), new Human(Integer.parseInt(w[10]))));

                //  command.execute();

            } else if (w[0].compareTo("Script") == 0) {
                if (w.length < 2) {
                    s.add("Не хватает данных для выполнения команды Script, пожалуйста, исправьте данные файла");
                    continue;
                }
                else if (w[1].compareTo(String.valueOf(file))==0){
                    s.add("Обнаружена попытка зациклить программу!!!Измените имя файла");
                }
                else
                command = new Command_Script(new File(w[1]));
                }

            else{ String input = w[0];
            if ((input.compareTo("Help") == 0) || (input.compareTo("help") == 0)) {
                command = new Command_Help();
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
            }}

          s.add(String.valueOf(command.execute()));
        }

         return s;
    }}
