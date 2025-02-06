package Ejercicio08_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Mostrar todos los datos de los profesores y de los cursos de los que son tutores. Si no son
     tutores, que aparezca “Este profesor no es tutor” en lugar de los datos del curso.**/


    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
            Statement statement = conexion.createStatement();
            String query = "SELECT * FROM profesor p LEFT JOIN curso c ON p.codProf = c.codTutor";
            ResultSet result = statement.executeQuery(query);

            System.out.println("Datos de la consulta: ");

            while(result.next()){
                if(result.getString("codOE") == null){
                    System.out.printf("Profesor %d -> clave: %s, nombre: %s, apellidos: %s, fecha de alta: %s - Este profesor no es tutor\n", result.getRow() ,result.getString(1),
                            result.getString(2), result.getString(3), result.getString(4));
                }else{
                    System.out.printf("Profesor %d -> clave: %s, nombre: %s, apellidos: %s, fecha de alta: %s, oferta educativa: %s, curso: %s, tutor: %s\n", result.getRow() ,result.getString(1),
                            result.getString(2), result.getString(3), result.getString(4), result.getString(5),
                            result.getString(6),result.getString(7));
                }

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
