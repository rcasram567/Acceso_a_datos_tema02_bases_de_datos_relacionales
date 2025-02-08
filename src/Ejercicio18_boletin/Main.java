package Ejercicio18_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Realiza un procedimiento en MySql que dado un curso y una asignatura, devuelva el
     número de horas semanales que tiene la asignatura y el nombre del profesor que la imparte.
     Haz un programa en java que llame al procedimiento y muestre por consola los resultados.**/

    /**PROCEDURE `getWeekHoursAndTeacherOfSubject`(oe VARCHAR(3), cur VARCHAR(2), asig VARCHAR(6), OUT horasSem INT, OUT profesor VARCHAR(61) )
     BEGIN
     SET horasSem = -1;
     SET profesor = 'INEXISTENTE';
     SELECT a.horasSemanales , CONCAT(p.nombre, ' ' ,p.apellidos) INTO horasSem, profesor
     FROM profesor p JOIN reparto r ON p.codProf = r.codProf
     JOIN asignatura a ON r.codAsig = a.codAsig
     WHERE r.codCurso = cur AND r.codOe = oe AND r.codAsig = asig;
     END**/
    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            getWeekHoursAndTeacherOfSubject("2A", "DAM", "HLC");
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void getWeekHoursAndTeacherOfSubject(String codCurso, String codOe, String codAsig) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        String query = "{call getWeekHoursAndTeacherOfSubject (?, ?, ?, ?, ?)}";
        CallableStatement callableStatement = conexion.prepareCall(query);

        callableStatement.setString(1, codOe);
        callableStatement.setString(2, codCurso);
        callableStatement.setString(3, codAsig);
        callableStatement.registerOutParameter(4,Types.INTEGER);
        callableStatement.registerOutParameter(5, Types.VARCHAR);

        callableStatement.executeUpdate();

        System.out.printf("La asignatura %s del curso %s de %s se da %d a la semana por el profesor %s \n", codAsig, codCurso, codOe, callableStatement.getInt(4), callableStatement.getString(5) );

        callableStatement.close();
        conexion.close();
    }
}
