
package com.mycompany.avance2;
import javax.swing.*;
public class VentanaCrearCuenta extends JFrame {
    
    public VentanaCrearCuenta(Banco banco){

        String pinTexto = JOptionPane.showInputDialog("Ingrese PIN para la cuenta");

        try{

            int pin = Integer.parseInt(pinTexto);

            Cuenta nueva = banco.crearCuenta(pin);

            JOptionPane.showMessageDialog(null,
                    "Cuenta creada correctamente\nNumero de cuenta: "
                    + nueva.getNumeroCuenta());

        }catch(Exception e){

            JOptionPane.showMessageDialog(null,"Datos inválidos");

        }

    }
}
