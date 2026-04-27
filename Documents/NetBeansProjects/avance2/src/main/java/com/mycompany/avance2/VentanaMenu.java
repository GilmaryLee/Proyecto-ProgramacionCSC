
package com.mycompany.avance2;
import javax.swing.*;
public class VentanaMenu extends JFrame {
    
    private int numeroCuenta;

    public VentanaMenu(int numeroCuenta){

        this.numeroCuenta = numeroCuenta;

        setTitle("Menu");
        setSize(300,400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnDeposito = new JButton("Depositar");
        btnDeposito.setBounds(80,20,130,30);
        add(btnDeposito);

        JButton btnRetiro = new JButton("Retirar");
        btnRetiro.setBounds(80,70,130,30);
        add(btnRetiro);

        JButton btnSaldo = new JButton("Saldo");
        btnSaldo.setBounds(80,120,130,30);
        add(btnSaldo);

        JButton btnHistorial = new JButton("Historial");
        btnHistorial.setBounds(80,170,130,30);
        add(btnHistorial);

        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.setBounds(80,220,130,30);
        add(btnTransferir);

        JButton btnLogout = new JButton("Cerrar sesión");
        btnLogout.setBounds(80, 320, 130, 30);
        add(btnLogout);

        btnDeposito.addActionListener(e -> {
            try{
                double monto = Double.parseDouble(
                    JOptionPane.showInputDialog("Monto:")
                );

                ConexionCliente con = new ConexionCliente();
                con.enviar("deposito", numeroCuenta, monto);

                JOptionPane.showMessageDialog(null,"OK");

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error");
            }
        });

        btnRetiro.addActionListener(e -> {
            try{
                double monto = Double.parseDouble(
                    JOptionPane.showInputDialog("Monto:")
                );

                ConexionCliente con = new ConexionCliente();
                String res = (String) con.enviar("retiro", numeroCuenta, monto);

                JOptionPane.showMessageDialog(null,res);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error");
            }
        });

        btnSaldo.addActionListener(e -> {
            try{
                ConexionCliente con = new ConexionCliente();
                double saldo = (double) con.enviar("saldo", numeroCuenta);

                JOptionPane.showMessageDialog(null,"Saldo: ₡" + saldo);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error");
            }
        });

        btnHistorial.addActionListener(e -> {
            try{
                ConexionCliente con = new ConexionCliente();
                String hist = (String) con.enviar("historial", numeroCuenta);

                JOptionPane.showMessageDialog(null,hist);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error");
            }
        });

        btnTransferir.addActionListener(e -> {
            try{
                int destino = Integer.parseInt(
                    JOptionPane.showInputDialog("Cuenta destino:")
                );

                // Validacion de la tranferencia
                if(destino == numeroCuenta){
                    JOptionPane.showMessageDialog(null,"No puedes transferirte a ti mismo");
                    return;
                }

                double monto = Double.parseDouble(
                    JOptionPane.showInputDialog("Monto:")
                );

                ConexionCliente con = new ConexionCliente();
                String res = (String) con.enviar("transferencia", numeroCuenta, destino, monto);

                JOptionPane.showMessageDialog(null,res);

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error");
            }
        });

        // Cierra sesion
        btnLogout.addActionListener(e -> {
            new VentanaLogin();
            dispose();
        });

        setVisible(true);
    }
}