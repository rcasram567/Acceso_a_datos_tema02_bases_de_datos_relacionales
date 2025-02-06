package Ejercicio04_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Aumenta las horas semanales y las horas totales en un 10% de aquellas asignaturas de la FP
     Básica que empiecen por M.**/

    public static void main(String[] args) {
        new Main().show();
    }

    private void show() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
            String queryUpdateSubjectWithCondition = "UPDATE asignatura a, reparto r " +
                    "SET horasTotales = horasTotales*1.1, " +
                    "horasSemanales = horasSemanales*1.1 " +
                    "WHERE a.codAsig = r.codAsig " +
                    "AND r.codOE = 'FBP' " +
                    "AND UPPER(nombre) LIKE 'M%'";

            Statement statement = conexion.createStatement();
            int updatedRows = statement.executeUpdate(queryUpdateSubjectWithCondition);

            System.out.println(updatedRows + " was updated in database");

            statement.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
