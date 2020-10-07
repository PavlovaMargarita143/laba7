package client;
import com.company.*;
import com.company.Reader;
import server.Command_Disconnect;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private static String login="";

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Client.login = login;
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        int chance=0;
    ByteBuffer receiveData = ByteBuffer.allocate(1000000);
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress((int) (Math.random() * 6000) +2000));
        InetAddress serverIP = readIP();
        while (true) {
            if(SendData(channel, serverIP)<0){chance++;if(chance==5){ System.exit(0);} continue;}
            Thread.sleep(1500);
            showAnswer(ReceiveData(receiveData, channel));
        }
    }



    private static int SendData(DatagramChannel channel, InetAddress serverIP) {
        try {
            if(serverIP.isReachable(1000)) {
            channel.connect(new InetSocketAddress(serverIP, 6667));
            System.out.println("Готовим запрос к отправке ");
            Reader reader =new Reader();
            Command command = reader.ReadCommand();
            if (command instanceof Command_Exit) {

                channel.send(ByteBuffer.wrap(Serializer.serialize(new Command_Disconnect())), new InetSocketAddress(serverIP, 6667));
                System.exit(0);
            }
            else if (command instanceof Command_Save) {
                System.out.println( "У вас нет доступа к этой команде" );
                channel.disconnect();
                return 1;
            }
                if(command instanceof Command_Add) ((Command_Add) command).setLogin(login);
                else  if(command instanceof Command_Clear) ((Command_Clear) command).setLogin(login);
                else  if(command instanceof Command_RemoveById) ((Command_RemoveById) command).setLogin(login);
                else  if(command instanceof Command_RemoveAt)((Command_RemoveAt) command).setLogin(login);
                else  if(command instanceof Command_UpdateId)((Command_UpdateId) command).setLogin(login);
                channel.send(ByteBuffer.wrap(Serializer.serialize(command)), new InetSocketAddress(serverIP, 6667));
            System.out.println("Отправили");
            channel.disconnect();
            return 1;}
            System.out.println("Сервер временно недоступен!");
            return  -1;
        } catch (IOException e) {
            System.err.println("IOException " + e);
            return -1;

        }
    }
private static int c = 0;
    private static ArrayList<String> ReceiveData(ByteBuffer receiveData, DatagramChannel channel) {

        try {
            receiveData.clear();
            System.out.println("Ожидаем ответ...");
            SocketAddress from = channel.receive(receiveData);
            ArrayList<String> answer = new ArrayList<>();
            if (from != null) {
                receiveData.flip();
                answer = (ArrayList<String>) Serializer.deserialize(receiveData.array());
            }


            return answer;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("IOException Receive " + e);
            return null;
        }
    }
    private static void showAnswer(ArrayList<String> answer) {
        System.out.println("Ответ сервера: ");
        if(answer.isEmpty()){
            System.out.println("Сервер временно недоступен или вы подключились не к тому серверу, попробуйте заново, если сервер не ответит, программа завершится");
            c++;
            if (c>2){
                System.exit(0);} ;
            return;
        }

        for (String str : answer) {
            System.out.println("\t" + str);
        }
        String string=answer.get(0).trim();
       if(string.substring(0,string.lastIndexOf(" ")).equalsIgnoreCase("Successful authorization")||
                string.substring(0,string.lastIndexOf(" ")).equalsIgnoreCase("Successful registration")){
            setLogin(string.substring(string.lastIndexOf(" ")+1));
        }
    }

    private static InetAddress readIP() throws UnknownHostException {
        InetAddress server=null;
        while (true) {
            System.out.print("Введите IP адресс сервера:");
            Scanner scanner = new Scanner(System.in);
            try {
                server = InetAddress.getByName(scanner.nextLine());
            }catch (UnknownHostException e){
                System.out.println("Неверный IP!");
                continue;
            }
            break;
        }
        return server;
    }
}
