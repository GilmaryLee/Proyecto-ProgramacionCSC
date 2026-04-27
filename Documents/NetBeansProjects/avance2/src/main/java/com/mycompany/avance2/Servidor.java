
package com.mycompany.avance2;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {

        try{
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Servidor iniciado...");

            while(true){
                Socket cliente = server.accept();
                new ClienteHandler(cliente).start();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
