package Ejercicio03_boletin;

import java.sql.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {

        try{
            int updatedRows = 0;
            //updatedRows += insertSubject("OACE", "Operaciones auxiliares para la configuración y la explotación", 7, 245);
            //updatedRows += insertSubject("MMSCI", "Montaje y mantenimiento de sistemas y componentes informaticos", 9, 315);

            //updatedRows += insertTeacher("JSL", "Jose", "Lobato Garcia", Timestamp.valueOf("2025-01-22 00:00:00"));
            //updatedRows += insertTeacher("MPR", "Monica", "Parejo Ramirez", Timestamp.valueOf("2025-01-22 00:00:00"));

            updatedRows += insertCourse("FBP", "1A", "JSL");

            updatedRows += insertAssigment("FBP", "1A", "OACE", "JSL");
            updatedRows += insertAssigment("FBP", "1A", "MMSCI", "MPR");

            System.out.println(updatedRows + " was updated in database");

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public Integer insertCourse(String codOE, String codCourse, String codTutor) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String psSqlInsertCourse = "INSERT INTO curso VALUES(?,?,?)";
        PreparedStatement psInsertCourse = conexion.prepareStatement(psSqlInsertCourse);
        int rowsUpdated;

        psInsertCourse.setString(1, codOE);
        psInsertCourse.setString(2, codCourse);
        psInsertCourse.setString(3, codTutor);
        rowsUpdated = psInsertCourse.executeUpdate();

        psInsertCourse.close();
        conexion.close();
        return  rowsUpdated;
    }

    public Integer insertTeacher(String codTeacher, String name, String surname, Timestamp hireDate) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String psSqlInsertTeacher = "INSERT INTO profesor VALUES(?,?,?,?)";
        PreparedStatement psInsertTeacher = conexion.prepareStatement(psSqlInsertTeacher);
        int rowsUpdated;

        psInsertTeacher.setString(1, codTeacher);
        psInsertTeacher.setString(2, name);
        psInsertTeacher.setString(3, surname);
        psInsertTeacher.setTimestamp(4, hireDate);
        rowsUpdated =  psInsertTeacher.executeUpdate();

        psInsertTeacher.close();
        conexion.close();
        return rowsUpdated;
    }

    public Integer insertSubject(String codSubject, String name, Integer weekHours, Integer totalHours) throws SQLException{
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String psSqlInsertSubject = "INSERT INTO asignatura VALUES(?,?,?,?)";
        PreparedStatement psInsertSubject = conexion.prepareStatement(psSqlInsertSubject);
        int rowsUpdated;

        psInsertSubject.setString(1, codSubject);
        psInsertSubject.setString(2, name);
        psInsertSubject.setInt(3, weekHours);
        psInsertSubject.setInt(4, totalHours);
        rowsUpdated = psInsertSubject.executeUpdate();

        psInsertSubject.close();
        conexion.close();
        return rowsUpdated;
    }

    public Integer insertAssigment(String codOE, String codCourse, String codSubject, String codTeacher) throws SQLException{
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String psSqlInsertAssigment = "INSERT INTO reparto VALUES(?,?,?,?)";
        PreparedStatement psInsertAssigment = conexion.prepareStatement(psSqlInsertAssigment);
        int rowsUpdated;

        psInsertAssigment.setString(1, codOE);
        psInsertAssigment.setString(2, codCourse);
        psInsertAssigment.setString(3, codSubject);
        psInsertAssigment.setString(4, codTeacher);
        rowsUpdated = psInsertAssigment.executeUpdate();

        psInsertAssigment.close();
        conexion.close();
        return rowsUpdated;
    }
}
