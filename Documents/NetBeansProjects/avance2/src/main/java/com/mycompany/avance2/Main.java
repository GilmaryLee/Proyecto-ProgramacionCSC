
package com.mycompany.avance2;


public class Main {

    public static void main(String[] args) {
        
         Banco banco = new Banco();

        banco.crearCuenta(1234);

        new VentanaLogin(banco);

    }
       
}
