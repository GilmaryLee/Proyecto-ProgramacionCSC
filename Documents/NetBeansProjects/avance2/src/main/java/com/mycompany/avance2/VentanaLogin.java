
package com.mycompany.avance2;
import javax.swing.*;

public class VentanaLogin extends JFrame {
    
    private JTextField txtCuenta;
    private JPasswordField txtPin;

    public VentanaLogin(){

        setTitle("Cajero");
        setSize(300,250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel l1 = new JLabel("Cuenta:");
        l1.setBounds(30,30,80,25);
        add(l1);

        txtCuenta = new JTextField();
        txtCuenta.setBounds(120,30,120,25);
        add(txtCuenta);

        JLabel l2 = new JLabel("PIN:");
        l2.setBounds(30,70,80,25);
        add(l2);

        txtPin = new JPasswordField();
        txtPin.setBounds(120,70,120,25);
        add(txtPin);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(90,110,100,30);
        add(btnLogin);

        JButton btnCrear = new JButton("Crear Cuenta");
        btnCrear.setBounds(80,150,130,30);
        add(btnCrear);

        btnLogin.addActionListener(e -> {

            try{

                if(txtCuenta.getText().isEmpty() || txtPin.getPassword().length == 0){
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                    return;
                }

                int cuenta = Integer.parseInt(txtCuenta.getText());
                int pin = Integer.parseInt(new String(txtPin.getPassword()));

                ConexionCliente con = new ConexionCliente();

                boolean acceso = (boolean) con.enviar("login", cuenta, pin);

                if(acceso){
                    new VentanaMenu(cuenta);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Datos incorrectos");
                }

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
            }
        });

        btnCrear.addActionListener(e -> new VentanaCrearCuenta());

        setVisible(true);
    }
}