
package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class SesionUsuario {

    private static Connection conexionActual;
    private static ResultadoLogin usuarioActual;

    private SesionUsuario() {
    }

    // Iniciar sesión según el rol
    public static void iniciar(ResultadoLogin resultado)
            throws SQLException {

        if (resultado == null) {
            throw new SQLException("No existe información del usuario.");
        }

        String rol = resultado.getNombreRol();

        if (rol == null || rol.trim().isEmpty()) {
            throw new SQLException("El usuario no tiene un rol asignado.");
        }

        // Conectar con el usuario correspondiente al rol
        Connection nuevaConexion = conexion.conectarPorRol(rol);

        if (nuevaConexion == null || nuevaConexion.isClosed()) {
            throw new SQLException("No se pudo establecer la conexión.");
        }

        conexionActual = nuevaConexion;
        usuarioActual = resultado;
    }

    // Comprobar si existe una conexión activa
    public static boolean estaActiva() {

        try {
            return conexionActual != null
                    && !conexionActual.isClosed()
                    && conexionActual.isValid(3);

        } catch (SQLException e) {
            return false;
        }
    }

// OBTENER LA CONEXIÓN DEL USUARIO ACTUAL.
public static Connection obtenerConexion() throws SQLException {

    if (!estaActiva()) {
        throw new SQLException(
                "No existe una conexión activa con Oracle "
                + "para el usuario que inició sesión."
        );
    }

    return conexionActual;
}

    // Obtener usuario actual
    public static ResultadoLogin obtenerUsuario() {
        return usuarioActual;
    }

    // Cerrar sesión
    public static void cerrar() {

        try {
            if (conexionActual != null && !conexionActual.isClosed()) {
                conexionActual.close();
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al cerrar conexión: " + e.getMessage()
            );

        } finally {

            conexionActual = null;
            usuarioActual = null;
        }
    }
}
