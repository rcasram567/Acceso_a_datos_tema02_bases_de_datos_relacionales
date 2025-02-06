package Ejercicio15_boletin;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    /**15. Mostrar dónde se encuentra un profesor ahora, es decir, en el momento actual.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {

            whereIsTeacherAt( "EPM");

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void whereIsTeacherAt( String codProf) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true","root", "root");
        Statement statement = conexion.createStatement();
        String query = String.format("""
                SELECT p.nombre, p.apellidos, h.codCurso, h.codOe 
                FROM profesor p 
                JOIN reparto r ON p.codProf = r.codProf 
                JOIN horario h ON r.codOe = h.codOe AND r.codCurso = h.codCurso AND h.codAsig = r.codAsig 
                JOIN tramohorario th ON h.codTramo = th.codTramo 
                WHERE th.dia = dayname(curdate()) AND (curtime() BETWEEN th.horaInicio AND th.horaFin) 
                AND p.codProf = '%s'""", codProf);

        statement.execute("SET lc_time_names = 'es_ES';");
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
