
package com.mycompany.avance2;


public abstract class Transaccion { //Clase abstracta
    
    //Atributos
    protected double monto;
    protected String tipo;
    
    //Constructor
    public Transaccion(double monto, String tipo) {
        this.monto = monto;
        this.tipo = tipo;
    }
    
    //Getters
    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }
    
    //Mostrar detalle
    public String mostrarDetalle(){
        return tipo + " de ₡" + monto;
    }
}
