package com.centdb.module6;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.centdb.constants.DatabaseConstants;
import com.centdb.constants.DatabaseConstants.RegExConstants;
import com.centdb.util.Utility;

public class Export {

    private File database;
    private List<File> tables;
    private List<File> metaDataTables;

    public Export(String database) {
        this.database = getDatabase(database);
        if (!this.database.exists()) {
            System.out.println("\nNo such database exists.");

        }
        tables = getTables(this.database);
        metaDataTables = getMetaDataTables(this.database);
    }

    private File getDatabase(String database) {
        return Paths.get(DatabaseConstants.DATABASE_PATH, database).toFile();
    }

    private List<File> getTables(File database) {
        File[] files = database
                .listFiles((FilenameFilter) (database1, name) -> name.endsWith(DatabaseConstants.TABLE_SUFFIX));
        Arrays.sort(files);
        return new ArrayList<>(Arrays.asList(files));
    }

    private List<File> getMetaDataTables(File database) {
        File[] files = database
                .listFiles((FilenameFilter) (database1, name) -> name.endsWith(DatabaseConstants.METADATA_SUFFIX));
        Arrays.sort(files);
        return new ArrayList<>(Arrays.asList(files));
    }

    private String generateSqlCreateTableQuery(File metaDataTable) {
        String sqlCreateQuery = "CREATE TABLE `" + metaDataTable.getName().split("\\.")[0] + "` (";

        List<String> columns = getRows(metaDataTable);
        String columnString = "\n";
        String constraints = "";
        for (int i = 0; i < columns.size(); i++) {
            columnString = "";
            List<String> columnSplit = new ArrayList<>(
                    Arrays.asList(columns.get(i).split(RegExConstants.DELIMITER_REGEX)));

            columnString += "`" + columnSplit.get(0) + "` ";
            columnString += Utility.getEquivalentSqlDatatype(columnSplit.get(1)) + " ";

            if (columnSplit.size() == 3) {
                columnString += columnSplit.get(2).toUpperCase() + " ";
            }

            // Adds foreign key references at table level which is
            // SUPPORTED ON: SQL Server / Oracle / MS Access
            // NOT SUPPORTED ON: MySQL

            // else if (columnSplit.size() == 5) {
            // columnString += columnSplit.get(2).toUpperCase() + " REFERENCES `" +
            // columnSplit.get(3)
            // + "`(`" + columnSplit.get(4) + "`)";
            // }

            if (i != columns.size() - 1) {
                columnString += ",\n";
            }

            sqlCreateQuery += columnString;
        }

        sqlCreateQuery += ");";

        return sqlCreateQuery;
    }

    private String generateSqlInsertQuery(File table, File metaDataTable) {
        String sqlInsertQuery = "";
        List<String> metaDataRows = getRows(metaDataTable);
        List<String> columnDataTypes = new ArrayList<>();

        metaDataRows.forEach((metaDataRow) -> {
            columnDataTypes.add(metaDataRow.split(RegExConstants.DELIMITER_REGEX)[1]);
        });

        List<String> rows = getRows(table);
        if (rows.size() == 0) {
            return sqlInsertQuery;
        }

        sqlInsertQuery += "INSERT INTO `" + table.getName().split("\\.")[0] + "` VALUES ";
        for (int i = 0; i < rows.size(); i++) {
            List<String> rowSplit = new ArrayList<>(
                    Arrays.asList(rows.get(i).split(RegExConstants.DELIMITER_REGEX)));

            sqlInsertQuery += "(";
            for (int j = 0; j < rowSplit.size(); j++) {
                switch (columnDataTypes.get(j)) {
                    case "int":
                        sqlInsertQuery += rowSplit.get(j);
                        break;
                    case "varchar":
                        sqlInsertQuery += "'" + rowSplit.get(j) + "'";
                        break;
                    default:
                        sqlInsertQuery += "";
                }
                if (j != rowSplit.size() - 1) {
                    sqlInsertQuery += ",";
                }
            }
            sqlInsertQuery += ")";
            if (i != rows.size() - 1) {
                sqlInsertQuery += ",";
            }
        }
        sqlInsertQuery += ";";

        return sqlInsertQuery;
    }

    private String generateSqlDropTableQuery(File table) {
        return "DROP TABLE IF EXISTS `" + table.getName().split("\\.")[0] + "`;";
    }

    private String generateSqlLockTableQuery(File table) {
        return "LOCK TABLES `" + table.getName().split("\\.")[0] + "` WRITE;";
    }

    private String getHeaders(File table) {
        return Utility.readFileByLines(table).get(0);
    }

    private List<String> getRows(File table) {
        List<String> tableRows = Utility.readFileByLines(table);
        tableRows.remove(0);
        return tableRows;
    }

    private String generateSqlUnlockTableQuery() {
        return "UNLOCK TABLES;";
    }

    @Override
    public String toString() {
        String sqlExportString = "";
        sqlExportString += "-- Database: " + database.getName().split("\\.")[0] + "\n";
        for (int i = 0; i < tables.size(); i++) {

            String tableName = tables.get(i).getName().split("\\.")[0];

            sqlExportString += "\n--\n";
            sqlExportString += "-- Table structure for table `" + tableName + "`\n";
            sqlExportString += "--\n\n";

            sqlExportString += generateSqlDropTableQuery(tables.get(i)) + "\n";
            sqlExportString += generateSqlCreateTableQuery(metaDataTables.get(i)) + "\n";

            sqlExportString += "\n--\n";
            sqlExportString += "-- Dumping data for table `" + tableName + "`\n";
            sqlExportString += "--\n\n";

            sqlExportString += generateSqlLockTableQuery(tables.get(i)) + "\n";
            sqlExportString += generateSqlInsertQuery(tables.get(i), metaDataTables.get(i)) + "\n";
            sqlExportString += generateSqlUnlockTableQuery() + "\n\n";
        }
        sqlExportString += "-- Dump completed on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return sqlExportString;
    }

    public static void showDatabases() {
        File exportsDir = new File(DatabaseConstants.DATABASE_PATH);
        for (File file : exportsDir.listFiles()) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
            }
        }
    }

    public void toSql() {
        try {

            File exportsDir = new File(DatabaseConstants.BASE_EXPORT_PATH);
            if (!exportsDir.exists()) {
                exportsDir.mkdir();
            }

            File sqlFile = Paths.get(DatabaseConstants.BASE_EXPORT_PATH, database.getName() + ".sql").toFile();
            if (sqlFile.createNewFile()) {
                System.out.println("\nFile created: " + database.getName() + ".sql");
            } else {
                System.out.println("\nFile already exists.");
            }
            FileWriter fileWriter = new FileWriter(sqlFile);
            fileWriter.write(this.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
