
package com.mycompany.avance2;
import java.util.ArrayList;
public class Cuenta {
    
    //Atributos
    private int numeroCuenta;
    private int pin;
    private double saldo;
    private ArrayList<Transaccion> historial;

    
    //Constructor
    public Cuenta(int numeroCuenta, int pin, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldoInicial;
        historial = new ArrayList<>();
    }
    
    //Getters 
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getPin() {
        return pin;
    }

    public double getSaldo() {
        return saldo;
    }

    //Depositar
    public void depositar (double monto){
        saldo += monto;
        historial.add(new Deposito(monto));
    }
    
    //retirar
    public void retirar (double monto) throws Exception{
        if (monto > saldo){
            throw new Exception("Saldo insuficiente");
        }
        
        saldo -= monto;
        historial.add(new Retiro(monto));
    }
    
    //mostrar historial
    public String mostrarHistorial(){
        String texto = "";
        
        for (Transaccion t: historial){
            texto += t.mostrarDetalle()+ "\n";
        }
        if (texto.equals("")){
            texto= "No hay transacciones";
        }
        return texto;
    }
    
}
