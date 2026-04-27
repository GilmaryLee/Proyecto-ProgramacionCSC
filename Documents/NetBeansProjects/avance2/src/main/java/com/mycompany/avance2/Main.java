
package com.mycompany.avance2;


public class Main {

    public static void main(String[] args) {

        new Thread(() -> {
            Servidor.main(null);
        }).start();

        new VentanaLogin();
    }
}
