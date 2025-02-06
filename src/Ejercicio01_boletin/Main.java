package Ejercicio01_boletin;

import java.sql.*;

public class Main {

    /**1. Mostrar información sobre las columnas de una tabla, sus claves primarias, las claves ajenas
     existentes en la tabla y las claves ajenas que utilizan la clave primaria de esta tabla.**/

    private void show(String tableName) {
        try {
            Connection conexion = DriverManager.getConnection( "jdbc:mysql://localhost/horario", "root", "root") ;
            DatabaseMetaData metaData = conexion.getMetaData();
            ResultSet columnsInfo = metaData.getColumns(null,"horario",tableName, null);
            ResultSet primaryKeysInfo = metaData.getPrimaryKeys(null, null, tableName);
            ResultSet importedKeysInfo = metaData.getExportedKeys(null, null, tableName);
            ResultSet exportedKeysInfo = metaData.getImportedKeys(null, null, tableName);
            String colName, colType, colSize, nula, pkDep= " ", separador= " ", fkName, pkName, pkTableName, fkTableName;
            int resultIterator;

            System.out.printf("-------------------INFORMACION DE LA TABLA %s: ----------------------\n", tableName);
            System.out.println("-------------------COLUMNAS: ----------------------");

            resultIterator = 0;
            while (columnsInfo.next()){
                colName = columnsInfo.getString("COLUMN_NAME");
                colType = columnsInfo.getString("TYPE_NAME");
                colSize = columnsInfo.getString("COLUMN_SIZE");
                nula = columnsInfo.getString("IS_NULLABLE");
                System.out.printf("%d - Informacion de la columna  %s: \n tipo: %s\n tamaño: %s\n es nullable? %s\n", resultIterator++, tableName,colName,colType,colSize,nula);
            }

            System.out.println(" ");
            System.out.println("-------------------CLAVES PRIMARIAS: ----------------------");
            resultIterator = 0;
            while (primaryKeysInfo.next()) {
                pkDep = pkDep + separador +
                        primaryKeysInfo.getString("COLUMN_NAME");//getString(4)
                separador = "+";

                System.out.printf("%d - Clave Primaria: %s\n",resultIterator++, pkDep);
            }
            System.out.println(" ");
            System.out.println("-------------------CLAVES FORÁNEAS IMPORTADAS A ESTA TABLA: ----------------------");
            resultIterator = 0;
            while (exportedKeysInfo.next()) {
                fkName = exportedKeysInfo.getString("FKCOLUMN_NAME");
                pkName = exportedKeysInfo.getString("PKCOLUMN_NAME");
                pkTableName = exportedKeysInfo.getString("PKTABLE_NAME");
                fkTableName = exportedKeysInfo.getString("FKTABLE_NAME");
                System.out.printf("%d - Tabla PK: %s, Clave Primaria: %s %n -------------- ", resultIterator++,pkTableName, pkName);
                System.out.printf("Tabla FK: %s, Clave Ajena: %s %n\n", fkTableName, fkName);
            }

            System.out.println(" ");
            System.out.println("-------------------CLAVES PRIMARIAS EXPORTADAS A OTRAS TABLAS: ----------------------");
            resultIterator = 0;
            while (importedKeysInfo.next()) {
                fkName = importedKeysInfo.getString("FKCOLUMN_NAME");
                pkName = importedKeysInfo.getString("PKCOLUMN_NAME");
                pkTableName = importedKeysInfo.getString("PKTABLE_NAME");
                fkTableName = importedKeysInfo.getString("FKTABLE_NAME");
                System.out.printf("%d - Tabla FK: %s, Clave Ajena: %s %n\n", resultIterator++,fkTableName, fkName);
            }



        } catch (SQLException e) {
            System.out.println("Error sql: "+e.getMessage());
        }

    }

    public static void main(String[] args) {
        new Main().show("reparto");
    }
}

