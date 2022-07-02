package com.example.educationsystem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private static ArrayList<User> users;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){

        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("new client connected!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2831);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
