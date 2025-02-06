package Ejercicio06_boletin;


import java.io.*;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Main {

    /**Realiza un programa que ejecute el script de la BD Horario.**/

    public static void main(String[] args) {
        new Main().show();
    }

    public void show() {
        try {
            // Estraemos como string las querys del archivo
            Path scriptSQL = Paths.get("C:\\Users\\hhhhh\\OneDrive\\Escritorio\\DAM\\Acceso a datos\\2TRIM\\SQL\\horario.sql");
            System.out.println("Fichero de consulta: "+scriptSQL.getFileName());
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(scriptSQL.toFile()));
            } catch (FileNotFoundException e) {
                System.err.println("Fichero no encontrado");
            }
            String linea = "", salto = System.lineSeparator(), consulta;
            StringBuilder stringBuilder = new StringBuilder();
            int result;
            try{
                while ((linea = reader.readLine()) != null){
                    stringBuilder.append(linea);
                    stringBuilder.append(salto);
                }
            } catch (IOException e){
                System.err.println("ERROR DE E/S: " + e.getMessage());
            }
            consulta = stringBuilder.toString();
            System.out.println("comsulta: " + consulta);

            //conectamos a base de datos y ejecutamos sql
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/horario?allowMultiQueries=true","root", "root");
            Statement statement = conexion.createStatement();
            result = statement.executeUpdate(consulta);
            System.out.println("Scrip ejecutado con exito, resultado = "+result);

            statement.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
