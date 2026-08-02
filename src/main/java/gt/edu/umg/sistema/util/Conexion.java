package gt.edu.umg.sistema.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase única de conexión a PostgreSQL. Ningún DAO abre su propia conexión;
 * todos piden la misma instancia a través de getConexion().
 *
 * AJUSTA estas tres constantes a tu entorno antes de correr el proyecto.
 */
public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_facturacion";
    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "posgres";

    private static Connection conexion;

    // Constructor privado: nadie puede hacer "new Conexion()" desde afuera.
    // Es lo que convierte esto en singleton.
    private Conexion() {
    }

    public static Connection getConexion() throws SQLException {
        // isClosed() cubre el caso donde Postgres cerró la conexión por timeout
        // y hay que abrir una nueva en vez de reusar una muerta.
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver de PostgreSQL no encontrado en el classpath.", e);
            }
        }
        return conexion;
    }
}
