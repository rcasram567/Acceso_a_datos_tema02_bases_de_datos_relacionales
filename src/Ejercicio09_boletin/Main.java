package Ejercicio09_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Del resultado de la consulta del ejercicio anterior, muestra el nombre de la columna, el tipo
     de dato de dicha columna, si puede contener valores nulos y el máximo ancho de caracteres
     de la columna.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
            Statement statement = conexion.createStatement();
            String query = "SELECT * FROM profesor p LEFT JOIN curso c ON p.codProf = c.codTutor";
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            String nulo;

            System.out.println("Datos de la consulta: ");
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                nulo = (resultSetMetaData.isNullable(i) == ResultSetMetaData.columnNoNulls) ? "NO" : "SI";

                System.out.printf("Column %d: \n\t Nombre de columna: %s\n\t Tipo de dato: %s\n\t ¿Puede contener valores nulos? -> %s\n\t Máximo ancho de caracteres: %d\n",
                        i, resultSetMetaData.getColumnName(i), resultSetMetaData.getColumnTypeName(i), nulo, resultSetMetaData.getColumnDisplaySize(i));
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
