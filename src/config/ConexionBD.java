package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String HOST = "localhost";
    private static final int PORT = 1433;
    private static final String DATABASE = "db_alquiler_peliculas";
    private static final String USER = "sa";
    private static final String PASSWORD = "Admin1234@";

    private static final String URL = String.format(
            "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=true;trustServerCertificate=true",
            HOST, PORT, DATABASE
    );

    private static Connection conexion = null;

    private ConexionBD() {}

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("[BD] Conexión establecida con SQL Server.");
            }
        } catch (SQLException e) {
            System.err.println("[Error] Error al conectar a SQL Server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("[Driver Error] No se encontró el driver JDBC.");
        }
        return conexion;
    }
}