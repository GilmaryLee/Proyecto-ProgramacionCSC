
package com.mycompany.avance2;
import javax.swing.*;

public class VentanaCrearCuenta extends JFrame {
    
    public VentanaCrearCuenta() {

        String nombre = JOptionPane.showInputDialog("Ingrese nombre");
        String identificacion = JOptionPane.showInputDialog("Ingrese identificación");
        String pinTexto = JOptionPane.showInputDialog("Ingrese PIN");

        try {
            int pin = Integer.parseInt(pinTexto);

            ConexionCliente con = new ConexionCliente();

            int numero = (int) con.enviar("crear", nombre, identificacion, pin);

            JOptionPane.showMessageDialog(null,
                    "Cuenta creada\nNumero: " + numero);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
