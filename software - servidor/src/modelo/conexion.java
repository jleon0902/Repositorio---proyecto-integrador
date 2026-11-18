
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static final String IP = "localhost";
    private static final String PUERTO = "1521";
    private static final String SID = "XE";

    private static final String URL =
            "jdbc:oracle:thin:@" + IP + ":" + PUERTO + ":" + SID;

    // CONEXIÓN PERSISTENTE DEL ADMINISTRADOR.
    private static Connection sesionAdministrador;

    // INICIAR SESIÓN DEL ADMINISTRADOR.
    public static synchronized void iniciarSesionAdministrador()
            throws SQLException {

        cerrarSesionAdministrador();

        sesionAdministrador = conectar(
                "ADMINISTRADOR",
                "admin123"
        );
    }

    // OBTENER CONEXIÓN DEL ADMINISTRADOR.
    public static synchronized Connection obtenerConexionAdministrador()
            throws SQLException {

        if (!estaConectadoAdministrador()) {
            throw new SQLException(
                "No existe una conexión activa con ADMINISTRADOR."
            );
        }

        return sesionAdministrador;
    }

    // VERIFICAR CONEXIÓN DEL ADMINISTRADOR.
    public static synchronized boolean estaConectadoAdministrador() {

        try {
            return sesionAdministrador != null
                    && !sesionAdministrador.isClosed()
                    && sesionAdministrador.isValid(2)
                    && "ADMINISTRADOR".equalsIgnoreCase(
                        sesionAdministrador.getMetaData().getUserName()
                    );

        } catch (SQLException e) {
            return false;
        }
    }

    // CERRAR CONEXIÓN DEL ADMINISTRADOR.
    public static synchronized void cerrarSesionAdministrador() {

        try {
            if (sesionAdministrador != null
                    && !sesionAdministrador.isClosed()) {

                sesionAdministrador.close();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        } finally {
            sesionAdministrador = null;
        }
    }

    // CONEXIÓN PARA VALIDAR CREDENCIALES.
    public static Connection conectarAdministrador()
            throws SQLException {

        return conectar("ADMINISTRADOR", "admin123");
    }

    // CONEXIÓN SEGÚN EL USUARIO DE ORACLE.
    public static Connection conectarPorRol(String nombreRol)
            throws SQLException {

        if (nombreRol == null) {
            throw new SQLException(
                    "El usuario no tiene un rol asignado."
            );
        }

        String rol = nombreRol.trim().toUpperCase();

        switch (rol) {

            case "SUPERADMINISTRADOR":
            case "ADMINISTRADOR":
                return conectar("ADMINISTRADOR", "admin123");

            case "ENCUESTADOR":
                return conectar("ENCUESTADOR", "enc123");

            case "RECUENTISTA":
                return conectar("RECUENTISTA", "rec123");

            case "CRITICO DIGITADOR":
            case "CRITICO_DIGITADOR":
                return conectar("CRITICO_DIGITADOR", "crit123");

            default:
                throw new SQLException(
                    "No existe una conexión configurada para: " + nombreRol
                );
        }
    }

    // CONEXIÓN GENERAL A ORACLE.
    private static Connection conectar(
            String usuario,
            String contrasena
    ) throws SQLException {

        return DriverManager.getConnection(
                URL,
                usuario,
                contrasena
        );
    }
}
