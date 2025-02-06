package Ejercicio14_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Mostrar dónde se encuentra un profesor en un tramo horario concreto.**/
    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {

            whereIsTeacherAt("L1", "MHP");

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void whereIsTeacherAt(String codTramo, String codProf) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();
        String query = String.format("""
                SELECT p.nombre, p.apellidos, h.codCurso, h.codOe
                FROM profesor p
                JOIN reparto r ON p.codProf = r.codProf 
                JOIN horario h ON r.codOe = h.codOe AND r.codCurso = h.codCurso AND h.codAsig = r.codAsig
                WHERE p.codProf = '%s' 
                AND h.codTramo = '%s'""", codProf, codTramo);
        ResultSet result = statement.executeQuery(query);

        System.out.println("Datos de la consulta: ");

        while(result.next()){
            if (result.getString(1) == null){
                System.out.println("El/La profesor/a no encuentra en ningun curso registardo");
            } else {
                System.out.printf("El/La profesor/a %s %s se encuentra en %s de %S\n",result.getString(1), result.getString(2), result.getString(3), result.getString(4));
            }

        }

        result.close();
        statement.close();
        conexion.close();
    }
}
