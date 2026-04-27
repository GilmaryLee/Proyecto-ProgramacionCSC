
package com.mycompany.avance2;
import java.net.Socket;
import java.io.*;

public class ConexionCliente {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ConexionCliente() throws Exception {
        socket = new Socket("localhost", 5000);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public Object enviar(String accion, Object... datos) throws Exception {

        out.writeObject(accion);

        for (Object d : datos) {
            out.writeObject(d); 
        }

        out.flush();

        Object respuesta = in.readObject();

        socket.close();

        return respuesta;
    }
}