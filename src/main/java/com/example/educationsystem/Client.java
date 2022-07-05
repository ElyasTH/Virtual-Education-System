package com.example.educationsystem;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private User user;
    private VBox messageBox;
    private Thread messageListener;

    public Client(Socket socket, User user) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.user = user;
        }catch (IOException e){
            e.printStackTrace();
            closeEverything();
        }
    }

    public void sendMessage(String message){
        try{
            bufferedWriter.write(user.getUsername() + ": " + message + "      " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    "/,/" + user.getId());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listenForMessage(VBox messageBox){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String receivedMessage;

                while (socket.isConnected()){
                    try{
                        receivedMessage = bufferedReader.readLine();
                        if (receivedMessage != null)
                            MessengerPageController.addLabel(receivedMessage, messageBox);
                    }catch (IOException e){
                        closeEverything();
                        break;
                    }
                }
            }
        });
        this.messageListener = thread;
        messageListener.start();
    }

    public void closeEverything(){
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
            messageListener.interrupt();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
