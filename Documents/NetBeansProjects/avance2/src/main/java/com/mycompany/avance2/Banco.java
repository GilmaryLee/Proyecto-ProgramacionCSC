
package com.mycompany.avance2;
import java.util.ArrayList;
public class Banco {
    
    private ArrayList<Cuenta> cuentas;
    private int contadorCuentas = 1;

    public Banco (){
        cuentas = new ArrayList<>();
    }
    
    public Cuenta crearCuenta (int pin){
        Cuenta nueva = new Cuenta (contadorCuentas, pin, 0);
        cuentas.add(nueva);
        contadorCuentas++;
        
        return nueva;
    }
    
    public Cuenta buscarCuenta (int numero){
        for (Cuenta c : cuentas){
            if (c.getNumeroCuenta()== numero){
                return c;
            }
        }
        return null;
    }
    
}
