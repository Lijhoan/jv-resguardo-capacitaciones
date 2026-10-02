package pe.jvresguardo.sigecap.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * HU-JVR-013
 * Provee la conexión JDBC a la base de datos sigecap_jv.
 * Usuario y contraseña se leen de variables de entorno, nunca hardcodeados.
 */
public final class DatabaseConnection {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_NAME = "sigecap_jv";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String host = valorOPorDefecto("DB_HOST", DEFAULT_HOST);
        String port = valorOPorDefecto("DB_PORT", DEFAULT_PORT);
        String name = valorOPorDefecto("DB_NAME", DEFAULT_NAME);
        String user = valorDeEntorno("DB_USER");
        String password = valorDeEntorno("DB_PASSWORD");

        // TiDB Cloud exige TLS; sslMode=REQUIRED cifra la conexion sin exigir
        // un truststore adicional, suficiente para este proyecto academico.
        String url = "jdbc:mysql://" + host + ":" + port + "/" + name
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&sslMode=REQUIRED";

        return DriverManager.getConnection(url, user, password);
    }

    private static String valorOPorDefecto(String variable, String porDefecto) {
        String valor = valorDeEntorno(variable);
        return (valor == null || valor.isBlank()) ? porDefecto : valor;
    }

    private static String valorDeEntorno(String variable) {
        String valor = System.getenv(variable);
        return valor == null ? null : valor.trim();
    }
}
