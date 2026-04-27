
package com.mycompany.avance2;
import java.net.Socket;
import java.io.*;
import java.sql.*;
import java.util.Date;

public class ClienteHandler extends Thread{
    
     Socket socket;

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String accion = (String) in.readObject();
            Connection con = ConexionBD.conectar();

            // Crear cuenta
            if (accion.equals("crear")) {

                String nombre = (String) in.readObject();
                String identificacion = (String) in.readObject();
                int pin = (int) in.readObject();

                int numero = (int) (Math.random() * 10000);

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO cuentas(numeroCuenta, pin, saldo, nombre, identificacion) VALUES(?,?,?,?,?)"
                );

                ps.setInt(1, numero);
                ps.setInt(2, pin);
                ps.setDouble(3, 0);
                ps.setString(4, nombre);
                ps.setString(5, identificacion);

                ps.executeUpdate();

                out.writeObject(numero);
            }

            // Login
            else if (accion.equals("login")) {

                int cuenta = (int) in.readObject();
                int pin = (int) in.readObject();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM cuentas WHERE numeroCuenta=? AND pin=?"
                );

                ps.setInt(1, cuenta);
                ps.setInt(2, pin);

                ResultSet rs = ps.executeQuery();
                out.writeObject(rs.next());
            }

            // Deposito
            else if (accion.equals("deposito")) {

                int cuenta = (int) in.readObject();
                double monto = (double) in.readObject();

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE cuentas SET saldo = saldo + ? WHERE numeroCuenta=?"
                );

                ps.setDouble(1, monto);
                ps.setInt(2, cuenta);
                ps.executeUpdate();

                guardarTransaccion(con, cuenta, "Deposito", monto);

                out.writeObject("OK");
            }

            // Retiro
            else if (accion.equals("retiro")) {

                int cuenta = (int) in.readObject();
                double monto = (double) in.readObject();

                PreparedStatement ps1 = con.prepareStatement(
                        "SELECT saldo FROM cuentas WHERE numeroCuenta=?"
                );

                ps1.setInt(1, cuenta);
                ResultSet rs = ps1.executeQuery();

                if (rs.next()) {

                    double saldo = rs.getDouble("saldo");

                    if (saldo >= monto) {

                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE cuentas SET saldo = saldo - ? WHERE numeroCuenta=?"
                        );

                        ps2.setDouble(1, monto);
                        ps2.setInt(2, cuenta);
                        ps2.executeUpdate();

                        guardarTransaccion(con, cuenta, "Retiro", monto);

                        out.writeObject("OK");

                    } else {
                        out.writeObject("Sin saldo");
                    }
                }
            }

            // Transferencia
            else if (accion.equals("transferencia")) {

                int origen = (int) in.readObject();
                int destino = (int) in.readObject();
                double monto = (double) in.readObject();

                con.setAutoCommit(false);

                try {

                    boolean error = false;

                    // Verificar destino
                    PreparedStatement psCheck = con.prepareStatement(
                            "SELECT * FROM cuentas WHERE numeroCuenta=?"
                    );
                    psCheck.setInt(1, destino);
                    ResultSet rsDestino = psCheck.executeQuery();

                    if (!rsDestino.next()) {
                        out.writeObject("Cuenta destino no existe");
                        con.rollback();
                        error = true;
                    }
                    
                    if (!error) {

                        PreparedStatement ps1 = con.prepareStatement(
                                "SELECT saldo FROM cuentas WHERE numeroCuenta=?"
                        );
                        ps1.setInt(1, origen);
                        ResultSet rs = ps1.executeQuery();

                        if (rs.next()) {

                            double saldo = rs.getDouble("saldo");

                            if (saldo >= monto) {

                                PreparedStatement ps2 = con.prepareStatement(
                                        "UPDATE cuentas SET saldo = saldo - ? WHERE numeroCuenta=?"
                                );
                                ps2.setDouble(1, monto);
                                ps2.setInt(2, origen);
                                ps2.executeUpdate();

                                PreparedStatement ps3 = con.prepareStatement(
                                        "UPDATE cuentas SET saldo = saldo + ? WHERE numeroCuenta=?"
                                );
                                ps3.setDouble(1, monto);
                                ps3.setInt(2, destino);
                                ps3.executeUpdate();

                                guardarTransaccion(con, origen, "Transferencia enviada", monto);
                                guardarTransaccion(con, destino, "Transferencia recibida", monto);

                                con.commit();
                                out.writeObject("OK");

                            } else {
                                out.writeObject("Saldo insuficiente");
                                con.rollback();
                            }

                        } else {
                            out.writeObject("Cuenta origen no existe");
                            con.rollback();
                        }
                    }

                } catch (Exception e) {

                    con.rollback();
                    e.printStackTrace();
                    out.writeObject("Error: " + e.getMessage());

                } finally {

                    con.setAutoCommit(true); 
                }
            }

            // Saldo
            else if (accion.equals("saldo")) {

                int cuenta = (int) in.readObject();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT saldo FROM cuentas WHERE numeroCuenta=?"
                );

                ps.setInt(1, cuenta);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.writeObject(rs.getDouble("saldo"));
                }
            }

            // Historial
            else if (accion.equals("historial")) {

                int cuenta = (int) in.readObject();

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM transacciones WHERE numeroCuenta=?"
                );

                ps.setInt(1, cuenta);
                ResultSet rs = ps.executeQuery();

                String historial = "";

                while (rs.next()) {
                    historial += rs.getString("tipo") + " ₡" +
                            rs.getDouble("monto") + " | " +
                            rs.getString("fecha") + "\n";
                }

                if (historial.equals("")) {
                    historial = "No hay transacciones";
                }

                out.writeObject(historial);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarTransaccion(Connection con, int cuenta, String tipo, double monto) throws Exception {

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO transacciones(numeroCuenta,tipo,monto,fecha) VALUES(?,?,?,?)"
        );

        ps.setInt(1, cuenta);
        ps.setString(2, tipo);
        ps.setDouble(3, monto);
        ps.setString(4, new Date().toString());

        ps.executeUpdate();
    }
}