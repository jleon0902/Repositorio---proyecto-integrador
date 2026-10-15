
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepresentanteDAO {

    // VALIDAR EL INICIO DE SESIÓN DEL REPRESENTANTE
    public String[] validarRepresentante(String usuario, String contrasena)
            throws SQLException {

        String sql = "SELECT CEDULA, NOMBRE FROM REPRESENTANTE "
                   + "WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException(
                    "No fue posible conectar con Oracle."
                );
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        return new String[]{
                            resultado.getString("CEDULA"),
                            resultado.getString("NOMBRE")
                        };
                    }

                    return null;
                }
            }
        }
    }

    // OBTENER EL ID DE LA ENCUESTA DEL REPRESENTANTE
    public int obtenerIdEncuestaPorCedula(String cedula)
            throws SQLException {

        String sql =
            "SELECT ID FROM ("
          + " SELECT ID FROM ENCUESTA "
          + " WHERE CEDULA_REPRESENTANTE = ? "
          + " ORDER BY FECHA_INICIO DESC NULLS LAST, ID DESC"
          + ") WHERE ROWNUM = 1";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException(
                    "No fue posible conectar con Oracle."
                );
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, cedula);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        return resultado.getInt("ID");
                    }

                    return -1;
                }
            }
        }
    }

    // VERIFICAR SI LA ENCUESTA CUMPLIÓ LOS 14 DÍAS
    // Y ACTUALIZAR SU ESTADO Y FECHA DE FINALIZACIÓN
    public boolean verificarYFinalizarEncuesta(int idEncuesta)
            throws SQLException {

        String actualizar =
            "UPDATE ENCUESTA "
          + "SET ESTADO = 'FINALIZADO', "
          + "FECHA_FIN = FECHA_INICIO + 14 "
          + "WHERE ID = ? "
          + "AND TRUNC(SYSDATE) >= TRUNC(FECHA_FIN) "
          + "AND UPPER(TRIM(ESTADO)) <> 'FINALIZADO'";

        String consultar =
            "SELECT ESTADO, FECHA_FIN "
          + "FROM ENCUESTA "
          + "WHERE ID = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException(
                    "No fue posible conectar con Oracle."
                );
            }

            // Actualizar la encuesta cuando llegue su fecha límite
            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(actualizar)) {

                sentencia.setInt(1, idEncuesta);
                sentencia.executeUpdate();
            }

            // Consultar el estado actual
            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(consultar)) {

                sentencia.setInt(1, idEncuesta);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {

                        String estado = resultado.getString("ESTADO");

                        return estado != null
                            && estado.trim().equalsIgnoreCase("FINALIZADO");
                    }

                    throw new SQLException(
                        "No se encontró la encuesta."
                    );
                }
            }
        }
    }

    // VALIDAR EL INICIO DE SESIÓN DEL ENCUESTADOR
    public String[] validarEncuestador(String usuario, String contrasena)
            throws SQLException {

        String sql =
            "SELECT CEDULA, NOMBRE, APELLIDO FROM ENCUESTADOR "
          + "WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException(
                    "No fue posible conectar con Oracle."
                );
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {

                        String cedula = resultado.getString("CEDULA");

                        String nombreCompleto =
                            resultado.getString("NOMBRE") + " "
                          + resultado.getString("APELLIDO");

                        return new String[]{
                            cedula,
                            nombreCompleto
                        };
                    }

                    return null;
                }
            }
        }
    }

    // VALIDAR EL INICIO DE SESIÓN DEL CRÍTICO-DIGITADOR
    public String validarCriticoDigitador(String usuario, String contrasena)
            throws SQLException {

        String sql =
            "SELECT NOMBRE, APELLIDO FROM CRITICO_DIGITADOR "
          + "WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException(
                    "No fue posible conectar con Oracle."
                );
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        return resultado.getString("NOMBRE") + " "
                             + resultado.getString("APELLIDO");
                    }

                    return null;
                }
            }
        }
    }

    // MÉTODO AUXILIAR PARA VALIDAR USUARIOS
    private String validarUsuario(
            String tabla,
            String usuario,
            String contrasena) throws SQLException {

        String sql =
            "SELECT NOMBRE FROM " + tabla
          + " WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException(
                    "No fue posible conectar con Oracle."
                );
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        return resultado.getString("NOMBRE");
                    }

                    return null;
                }
            }
        }
    }
}
