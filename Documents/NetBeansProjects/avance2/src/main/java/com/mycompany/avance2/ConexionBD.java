
package com.mycompany.avance2;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    
    public static Connection conectar(){
        try{
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banco",
                "root",
                "1234"
            );
        }catch(Exception e){
            System.out.println("Error BD: " + e.getMessage());
            return null;
        }
    }
}

