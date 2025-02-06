package Ejercicio13_boletin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    /**Mostrar el horario de un curso en modo tabla. Añadir un asterisco en aquellos tramos
     horarios donde haya desdoble.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {

            createSchedule();

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void showScheduleOfCourse(String codOe, String codCourse) throws SQLException {
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root", "root");
        Statement statement = conexion.createStatement();

        ResultSet result = statement.executeQuery(getQuerySubjectsOfHour("lunes", "DAM","2A"));
        ResultSetMetaData resultMetaData = result.getMetaData();
        int maxSize = 0;

        for (int i = 1; i <= resultMetaData.getColumnCount(); i++) {
            maxSize += resultMetaData.getColumnDisplaySize(i);
        }


        String sepX = "-", sepY = "|";

        System.out.println("Datos de la consulta: ");

        while(result.next()){


            String separador = sepY + sepX.repeat(maxSize) + sepY;
            System.out.println(separador);
            int cal = (separador.length() - result.getString(1).length() - 2) /2;
            double cald = ((double) separador.length() - (double) result.getString(1).length() - 2.0) /2.0%1.0;
            int ed, ei;
            if (cald == 0.5){
                ed = cal;
                ei = cal+1;
            } else {
                ed = cal; ei = cal;
            }
            System.out.printf("%s%s%s%s%s\n",
                    sepY," ".repeat(ei), result.getString(1)," ".repeat( ed),sepY);

        }
        System.out.println(sepY+sepX.repeat(200)+sepY);

        result.close();
        statement.close();
        conexion.close();
    }

    public void createSchedule() throws SQLException {
        List<List<String>> schedule = new ArrayList<>();
        List<String> startHours = getHours();
        startHours = startHours.stream().map(hour -> hour.substring(0,5)).toList();
        schedule.add(getDays());
        String sepX = "-", sepY = "|";
        int maxSizeRow = schedule.getFirst().stream().map(String::length).reduce(Integer::sum).get();
        String separator = sepY + sepX.repeat(maxSizeRow) + sepY;

        for (String hour : startHours) {
            List<String> row = getSubjectOfSpecificHour(hour, "DAM", "2A");
            row.addFirst(hour);
            schedule.addLast(row);
        }

        System.out.println(separator);
        for (List<String> row : schedule) {
            System.out.println(row);
            System.out.println(separator);
        }

        //String separador = sepY + sepX.repeat(maxSize) + sepY;
        //int cal = (separador.length() - result.getString(1).length() - 2) /2;
        //double cald = ((double) separador.length() - (double) result.getString(1).length() - 2.0) /2.0%1.0;
        //int ed, ei;
        //if (cald == 0.5){
        //    ed = cal;
        //    ei = cal+1;
        //} else {
        //    ed = cal; ei = cal;
        //}
    }

    public String getQuerySubjectsOfHour(String horaInicio, String codOe, String codCourse){
        return "SELECT h.codAsig " +
                "FROM horario h, tramohorario th " +
                "WHERE h.codTramo = th.codTramo " +
                "AND h.codCurso = '"+codCourse+"' " +
                "AND h.codOe = '"+codOe+"' " +
                "AND th.horaInicio = '"+horaInicio+"' " +
                "ORDER BY th.dia";
    }

    public List<String> getSubjectOfSpecificHour(String hour, String codOe, String codCourse) throws SQLException {
        List<String> orderedSubjects = new ArrayList<>();
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String query = getQuerySubjectsOfHour(hour, codOe, codCourse);
        Statement statement = conexion.createStatement();
        ResultSet result = statement.executeQuery(query);

        while(result.next()){
            orderedSubjects.add(result.getRow()-1, result.getString(1));
        }
        result.close();
        statement.close();
        conexion.close();
        return orderedSubjects;
    }

    public List<String> getDays() throws SQLException {
        List<String> days = new ArrayList<>();
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String query = "SELECT DISTINCT(dia) FROM tramohorario ORDER BY dia";
        Statement statement = conexion.createStatement();
        ResultSet result = statement.executeQuery(query);
        days.add("HORARIO");
        while(result.next()){
            days.add(result.getRow(), result.getString(1));
        }

        result.close();
        statement.close();
        conexion.close();
        return days;
    }

    public List<String> getHours() throws SQLException {
        List<String> dias = new ArrayList<>();
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario","root","root");
        String query = "SELECT DISTINCT(horaInicio) FROM tramohorario ORDER BY horaInicio";
        Statement statement = conexion.createStatement();
        ResultSet result = statement.executeQuery(query);
        while(result.next()){
            dias.add(result.getRow()-1, result.getString(1));
        }

        result.close();
        statement.close();
        conexion.close();
        return dias;
    }

}
