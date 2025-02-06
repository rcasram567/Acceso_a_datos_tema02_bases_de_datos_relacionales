package Ejercicio12_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Mostrar qué asignaturas imparte un profesor.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {

            searchSubjectOfTeacher("CMC");

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void searchSubjectOfTeacher(String codProf) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();
        String query = String.format("""
                SELECT a.nombre, p.nombre, p.apellidos, r.codCurso, r.codOe
                FROM profesor p 
                JOIN reparto r ON p.codProf = r.codProf 
                JOIN asignatura a ON r.codAsig = a.codAsig 
                WHERE p.codProf = '%s' """, codProf);
        ResultSet result = statement.executeQuery(query);

        System.out.println("Datos de la consulta: ");

        while(result.next()){
            if (result.isFirst()){
                System.out.printf("Asignaturas del profesor:  %s %s \n", result.getString(2), result.getString(3));
            }
            System.out.printf("\t - %s en el curso %s de %s\n",result.getString(1), result.getString(4), result.getString(5));

        }

        result.close();
        statement.close();
        conexion.close();
    }
}
