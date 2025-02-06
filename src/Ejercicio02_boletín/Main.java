package Ejercicio02_boletín;

import java.sql.*;

public class Main {

    /**Insertar la siguiente oferta educativa:
     cod_OE: FPB
     nombre: FP Básica Informática y comunicaciones
     descripción: La formación profesional básica de informática y comunicaciones tiene una
     duración de 2000 horas repartidas entre dos cursos académicos incluyendo 240 horas de
     Formación en centros de trabajo (FCT) en empresas del Sector**/

    public void show(){
        try {
            System.out.println("hola");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario", "root", "root");
            Statement sentencia = conexion.createStatement();
            String query = "INSERT INTO ofertaeducativa VALUES ('FBP', 'FP Básica Informática y comunicaciones', 'La formación profesional básica de informática y comunicaciones tiene una " +
                    "     duración de 2000 horas repartidas entre dos cursos académicos incluyendo 240 horas de " +
                    "     Formación en centros de trabajo (FCT) en empresas del Sector' , 'CFGM', '2025-01-22')";
            Integer result = sentencia.executeUpdate(query);
            System.out.println("El numero de registro agregados a la tabla oferta educativa fue: "+result);
            sentencia.close();
            conexion.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        new Main().show();
    }

}
