
package com.mycompany.avance2;
import javax.swing.*;
public class VentanaMenu extends JFrame {
    
    private Cuenta cuenta;

    public VentanaMenu(Cuenta cuenta){

        this.cuenta = cuenta;

        setTitle("Menu Cajero");
        setSize(300,330);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnDeposito = new JButton("Depositar");
        btnDeposito.setBounds(80,20,130,30);
        add(btnDeposito);

        JButton btnRetiro = new JButton("Retirar");
        btnRetiro.setBounds(80,70,130,30);
        add(btnRetiro);

        JButton btnSaldo = new JButton("Consultar saldo");
        btnSaldo.setBounds(80,120,130,30);
        add(btnSaldo);

        JButton btnHistorial = new JButton("Historial");
        btnHistorial.setBounds(80,170,130,30);
        add(btnHistorial);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(80,220,130,30);
        add(btnSalir);

        btnDeposito.addActionListener(e -> {

            String m = JOptionPane.showInputDialog("Monto a depositar");

            double monto = Double.parseDouble(m);

            cuenta.depositar(monto);

            JOptionPane.showMessageDialog(null,"Deposito realizado");

        });

        btnRetiro.addActionListener(e -> {

            try{

                String m = JOptionPane.showInputDialog("Monto a retirar");

                double monto = Double.parseDouble(m);

                cuenta.retirar(monto);

                JOptionPane.showMessageDialog(null,"Retiro realizado");

            }catch(Exception ex){

                JOptionPane.showMessageDialog(null,ex.getMessage());

            }

        });

        btnSaldo.addActionListener(e -> {

            JOptionPane.showMessageDialog(null,"Saldo: " + cuenta.getSaldo());

        });

        btnHistorial.addActionListener(e -> {

            JOptionPane.showMessageDialog(null,cuenta.mostrarHistorial());

        });

        btnSalir.addActionListener(e -> {

            JOptionPane.showMessageDialog(null,"Gracias por usar el cajero");

            System.exit(0);

        });

        setVisible(true);

    }
}
