package Ejercicio11_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /** Mostrar cuándo se imparte una asignatura en un curso concreto. **/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            searchWhenIsSubjectOfCourse("PROG","1A", "DAM");
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void searchWhenIsSubjectOfCourse(String codAsig, String codCurso, String codOe) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();
        String query = String.format("""
                SELECT th.dia, th.horaInicio, th.horaFin 
                FROM horario h JOIN tramohorario th ON h.codTramo = th.codTramo 
                WHERE codAsig = '%s' 
                AND codCurso = '%s' 
                and codOe = '%s' 
                ORDER BY th.dia, th.horaInicio
                """, codAsig, codCurso,codOe);
        ResultSet result = statement.executeQuery(query);

        System.out.println("Datos de la consulta: ");
        System.out.printf("Tramo horario de la asignatura %s del curso %s:\n", codAsig, codCurso);
        while(result.next()){

            System.out.printf("\t- %s de %s a %s\n",
                    result.getString(1), result.getString(2), result.getString(3));

        }

        result.close();
        statement.close();
        conexion.close();
    }

}
