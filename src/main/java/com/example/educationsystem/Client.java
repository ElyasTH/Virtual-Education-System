package com.example.educationsystem;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, User user) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = user.getUsername();
        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String message){
        try{
            bufferedWriter.write(username + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listenForMessage(VBox messageBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String receivedMessage;

                while (socket.isConnected()){
                    try{
                        receivedMessage = bufferedReader.readLine();
                        MessengerPageController.addLabel(receivedMessage, messageBox);
                    }catch (IOException e){
                        e.printStackTrace();
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (socket != null){
                socket.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
