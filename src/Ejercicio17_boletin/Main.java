package Ejercicio17_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /**Realiza una función en MySql que dado un curso, devuelva el nombre del tutor. Haz un
     programa en java que llame a dicha función y muestre por consola el resultado.**/

    /**DELIMITER //
     CREATE FUNCTION whoIsTutor(cur VARCHAR(2), oe VARCHAR(3))
     RETURNS VARCHAR(20)
     READS SQL DATA
     BEGIN
     DECLARE tutor VARCHAR(20) DEFAULT 'SIN TUTOR';
     SELECT p.nombre INTO tutor FROM curso c JOIN profesor p ON c.codTutor = p.codProf
     WHERE c.codCurso = cur AND c.codOe = oe;
     RETURN tutor;
     END;
     //
     DELIMITER ;**/
    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            whoIsTutor("2A", "DAM");
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void whoIsTutor(String codCurso, String codOe) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        String query = "{? = call whoIsTutor (?, ?)}";
        CallableStatement callableStatement = conexion.prepareCall(query);

        callableStatement.registerOutParameter(1, Types.VARCHAR);
        callableStatement.setString(2, codCurso);
        callableStatement.setString(3, codOe);

        callableStatement.executeUpdate();

        System.out.printf("El tutor del curso %s de %s es:  %s\n", codCurso, codOe, callableStatement.getString(1));

        callableStatement.close();
        conexion.close();
    }
}
