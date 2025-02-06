package Ejercicio16_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Mostrar de cada asignatura el nombre, el número de horas a la semana, el número de cursos
     distintos donde se imparte, el número de ofertas educativas distintas donde se imparte, de
     aquellas asignaturas que tengan 3 o más horas a la semana.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {

            getSubjectData( );

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void getSubjectData() throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();
        String query = String.format("""
                SELECT a.nombre, a.horasSemanales, COUNT(r.codCurso) AS 'en cuantos cursos se imparte', COUNT(r.codOe) AS 'en cuantas ofertas se imparte' 
                FROM asignatura a JOIN reparto r ON a.codAsig = r.codAsig 
                WHERE a.horasSemanales>2
                GROUP BY 1,2""");

        ResultSet result = statement.executeQuery(query);

        System.out.println("Datos de la consulta: ");

        while(result.next()){

            System.out.printf("\t%-60s: se imparte durante %s horas semanales en %s curso y en %s ofertas educativas\n",result.getString(1), result.getString(2), result.getString(3), result.getString(4));

        }

        result.close();
        statement.close();
        conexion.close();
    }

}
