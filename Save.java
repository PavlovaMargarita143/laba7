package server;

import java.io.IOException;
import java.util.Scanner;

public class Save extends Thread {
    private static String str = "";
    public void run() {

        System.out.println("Введите \"s\" если хотите сохранить коллекцию(на ввод есть 3с): ");
        Scanner in = new Scanner(System.in);
        long sTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - sTime < 3000)
        {
            try {
                if (System.in.available() > 0)
                {
                    str = in.nextLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    public static String getStr() {
        return str;
    }
}

