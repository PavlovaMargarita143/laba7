package server;

import com.company.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.postgresql.*;

public class Server {
    public Server(Database database) {
        commands = new HashMap<>();
        messages = new HashMap<>();
        Server.database = database;
        users = new HashMap<>();
    }

    private static HashMap<SocketAddress, ArrayList<Command>> commands;
    HashMap<SocketAddress, ArrayList<ArrayList<String>>> messages;
    private static HashMap<SocketAddress, String> users;

    static Database database;

    public void addUser(SocketAddress address) {
        users.put(address, "*");
    }

    public void changeUserStatus(SocketAddress address, String login) {
        users.replace(address, login);
    }

    private void removeUser(SocketAddress address) {
        users.remove(address);
    }

    private boolean userIsHere(SocketAddress address) {
        return users.containsKey(address);
    }


    public static Database getDatabase() {
        return database;
    }





    public void start() throws IOException {
        DatagramSocket send_sock = new DatagramSocket(6002);
        DatagramSocket receive_sock = new DatagramSocket(6667);
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ThreadPoolExecutor executor2 =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        ThreadPoolExecutor executor3 =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        executor.setCorePoolSize(1);
        executor2.setCorePoolSize(1);
        executor3.setCorePoolSize(1);
        executor.execute(() -> ReceiveData(receive_sock));
        Runnable send = () -> SendData(send_sock);
        Runnable execute = this::executeCommand;
        while (true) {
            if (executor2.getActiveCount() < 1) {
                if (!this.commands.isEmpty()) {
                    executor2.execute(execute);
                }
            }
            if (executor3.getActiveCount() < 1) {
                if (!this.messages.isEmpty()) {
                    executor3.execute(send);
                }
            }
        }
    }

    public HashMap<SocketAddress, ArrayList<ArrayList<String>>> getMessages() {
        return messages;
    }

    public void addMessage(SocketAddress address, ArrayList<String> strings) {
        if (this.messages.containsKey(address)) {
            this.messages.get(address).add(strings);
        } else {
            ArrayList<ArrayList<String>> stringss = new ArrayList<>();
            stringss.add(strings);
            this.messages.put(address, stringss);
        }
    }


    public void addCommandToList(SocketAddress address, Command command) {
        if (this.commands.containsKey(address)) {
            this.commands.get(address).add(command);
        } else {
            ArrayList<Command> commandd = new ArrayList<>();
            commandd.add(command);
            this.commands.put(address, commandd);
        }
    }

    public HashMap<SocketAddress, ArrayList<Command>> getCommands() {
        return commands;
    }


    public synchronized void SendData(DatagramSocket send_sock) {
        SocketAddress address = null;
        ArrayList<String> message = null;
        try {
            byte[] buffer;
            for (Map.Entry<SocketAddress, ArrayList<String>> set : this.getOneMessageFromList().entrySet()) {
                address = set.getKey();
                message = set.getValue();
                if (message.get(0).substring(0, message.get(0).lastIndexOf(" ")).
                        equalsIgnoreCase("Successful authorization") ||
                        message.get(0).substring(0, message.get(0).lastIndexOf(" ")).
                                equalsIgnoreCase("Successful registration")) {
                    changeUserStatus(address, message.get(0).substring(message.get(0).lastIndexOf(" ") + 1));
                } else if (message.get(0).equals("Disconnect ")) {
                    removeUser(address);
                    break;
                }
            }
            if (message == null) return;
            buffer = Serializer.serialize(message);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            send_sock.connect(address);
            send_sock.send(packet);
            send_sock.disconnect();
            Thread.yield();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }


    private void ReceiveData(DatagramSocket receive_socket) {
        while (true) {
            try {
                Command receiveCommand;
                //буфер для получения входящих данных
                byte[] buffer = new byte[65536];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                System.out.println("Ожидаем данные...");
                //Получаем данные
                receive_socket.receive(incoming);
                receive_socket.disconnect();
                receiveCommand = (Command) Serializer.deserialize(incoming.getData());
                System.out.println(" сообщение клиента: " + receiveCommand );
                System.out.println("Адресс: " + incoming.getAddress());
                System.out.println("Порт: " + incoming.getPort());
                if (!userIsHere(new InetSocketAddress(incoming.getAddress(), incoming.getPort()))) {
                    addUser(new InetSocketAddress(incoming.getAddress(), incoming.getPort()));
                }
                addCommandToList(new InetSocketAddress(incoming.getAddress(), incoming.getPort()), receiveCommand);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("IOException 2 " + e);
            }
        }
    }
    public synchronized HashMap<SocketAddress, ArrayList<String>> getOneMessageFromList() {
        SocketAddress address = null;
        for (Map.Entry<SocketAddress, ArrayList<ArrayList<String>>> message : this.messages.entrySet()) {
            address = message.getKey();
            if (address != null) break;
        }
        ArrayList<String> message = null;
        HashMap<SocketAddress, ArrayList<String>> result = new HashMap<>();
        if (messages.containsKey(address)) {
            if (this.messages.get(address).size() > 0) message = this.messages.get(address).get(0);
            this.messages.get(address).remove(0);
            if (this.messages.get(address).isEmpty()) this.messages.remove(address);
            result.put(address, message);
        }
        return result;
    }



    private boolean checkStatus(SocketAddress address, Command command) {
        //System.out.println(!this.users.get(address).equals("*"));
       if(!this.users.get(address).equals("*")) return true;
        if(!command.getStatus()) return true;
        else return false;
    }
    public synchronized HashMap<SocketAddress, Command> getOneCommandFromList() {
        HashMap<SocketAddress, Command> result = new HashMap<>();
        SocketAddress address = null;
        for (Map.Entry<SocketAddress, ArrayList<Command>> commands : getCommands().entrySet()) {
            address = commands.getKey();
            if (address != null) break;
        }
        Command command = null;
        if (commands.containsKey(address)) {
            if (commands.get(address).size() > 0) command = this.commands.get(address).get(0);
            this.commands.get(address).remove(0);
            if (this.commands.get(address).isEmpty()) this.commands.remove(address);
            result.put(address, command);
        }
        return result;
    }


    public synchronized void executeCommand() {
        Invoker invoker = new Invoker();
        for (Map.Entry<SocketAddress, Command> commands : this.getOneCommandFromList().entrySet()) {
            if (commands.getValue() != null) {

                if (checkStatus(commands.getKey(), (A_Command) commands.getValue())) {
                    invoker.setCommand(commands.getValue());
                } else {
                    this.addMessage(commands.getKey(), new ArrayList<String>(Collections.singleton("no such access rights")));
                    return;
                }
                try {
                    this.addMessage(commands.getKey(), invoker.executeCommand());
                } catch ( SQLException e) {
                    this.addMessage(commands.getKey(), new ArrayList<>(Collections.singleton(e.getMessage())));
                }

                Thread.yield();
            }
        }
    }


}