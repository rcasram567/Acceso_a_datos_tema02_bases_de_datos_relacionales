package Ejercicio05_boletin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Main {

    /**La FP Básica desaparece del IES Saladillo. Borra de la base de datos todo lo que sea de
     dicha oferta educativa.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
            String deleteFBPSubjectsQuery = "DELETE asignatura, reparto " +
                    "FROM asignatura " +
                    "JOIN reparto ON asignatura.codAsig = reparto.codAsig " +
                    "WHERE reparto.codOE = 'FBP'";
            String deleteFBPOeQuery = "DELETE FROM ofertaeducativa " +
                    "WHERE codOE = 'FBP' ";
            Statement statement = conexion.createStatement();
            int deleteRows;

            deleteRows = statement.executeUpdate(deleteFBPSubjectsQuery);
            System.out.println(deleteRows +" was deleted for deleteFBPSubjectsQuery");

            deleteRows = statement.executeUpdate(deleteFBPOeQuery);
            System.out.println(deleteRows +" was deleted for deleteFBPOeQuery");

            statement.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
