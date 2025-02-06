package Ejercicio07_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Mostrar todos los datos de los profesores ordenados por:
     a) Apellidos en orden ascendente.
     b) Fecha de alta en el instituto en orden descendente.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            selectTeacherOrderByAscSurname();
            System.out.println("--------------------------------------------------------------------------------------");
            selectTeacherOrderByDescHireDate();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void selectTeacherOrderByAscSurname() throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();
        String query = "SELECT * FROM profesor ORDER BY apellidos ASC";
        ResultSet result = statement.executeQuery(query);

        System.out.println("Datos de la consulta: Mostrar todos los datos de los profesores ordenados por Apellidos en orden ascendente");

        while(result.next()){
            System.out.printf("Registro %d -> clave: %s, nombre: %s, apellidos: %s, fecha de alta; %s\n", result.getRow() ,result.getString(1),
                    result.getString(2), result.getString(3), result.getString(4));
        }

        result.close();
        statement.close();
        conexion.close();
    }

    public void selectTeacherOrderByDescHireDate() throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();
        String query = "SELECT * FROM profesor ORDER BY fechaAlta DESC";
        ResultSet result = statement.executeQuery(query);

        System.out.println("Datos de la consulta: Mostrar todos los datos de los profesores ordenados por Fecha de alta en el instituto en orden descendente");

        while(result.next()){
            System.out.printf("Registro %d -> clave: %s, nombre: %s, apellidos: %s, fecha de alta; %s\n", result.getRow() ,result.getString(1),
                    result.getString(2), result.getString(3), result.getString(4));
        }

        result.close();
        statement.close();
        conexion.close();
    }
}
