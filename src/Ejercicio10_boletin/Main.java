package Ejercicio10_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Mostrar de todos los cursos el nombre de la oferta educativa, la clave primaria del curso y el
     nombre del tutor.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {

            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
            Statement statement = conexion.createStatement();
            String query = "SELECT o.nombre, c.codOe, c.codCurso, p.nombre " +
                    "FROM profesor p JOIN curso c ON p.codProf = c.codTutor " +
                    "JOIN ofertaeducativa o ON  o.codOe = c.codOe";
            ResultSet result = statement.executeQuery(query);

            System.out.println("Datos de la consulta: ");

            while(result.next()){

                System.out.printf("Column %d: \n\t Nombre oferta educativa: %s\n\t Codigo curso: %s-%s\n\t Nombre tutor: %s\n",
                        result.getRow(), result.getString(1), result.getString(2), result.getString(3), result.getString(4));

            }

            result.close();
            statement.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
